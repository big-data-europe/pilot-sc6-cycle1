package biz.poolparty.ppx.client.impl;

import biz.poolparty.ppx.client.api.request.ExtractRequest;
import biz.poolparty.ppx.client.api.response.extract.ExtractResponse;
import biz.poolparty.ppx.client.api.request.RequiredParameterMissingException;
import biz.poolparty.ppx.client.api.request.annotate.AnnotateRequest;
import biz.poolparty.ppx.client.api.request.categorization.CategorizationRequest;
import biz.poolparty.ppx.client.api.request.metadata.mapping.MetaDataMappingRequest;
import biz.poolparty.ppx.client.api.request.suggest.SuggestRequest;
import biz.poolparty.ppx.client.api.response.categorization.CategorizationResponse;
import biz.poolparty.ppx.client.api.response.metadata.mapping.MapResponse;
import biz.poolparty.ppx.client.api.response.suggest.SuggestResponse;
import biz.poolparty.ppx.client.impl.response.categorization.CategorizationResponseImpl;
import biz.poolparty.ppx.client.impl.response.extract.ExtractResponseImpl;
import biz.poolparty.ppx.client.impl.response.metadata.mapping.MapResponseImpl;
import biz.poolparty.ppx.client.impl.response.suggest.SuggestResponseImpl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;


import org.codehaus.jackson.map.ObjectMapper;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class PPXClientImpl extends HttpClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final URL ppxServer;
    private final String user;
    private final String pass;
    private final String credentials;
    
    private enum API_URL {

        API_ANNOTATE("/api/annotate"),
        API_EXTRACT("/api/extract"),
        API_SUGGEST("/api/suggest"),
        API_MAP("/api/map"),
        API_CATEGORIZATION("/api/categorization");
        

        private final String urlPath;

        private API_URL(String urlPath){
            this.urlPath = urlPath;
        }

        public String getUrl(URL ppxServer){
            return ppxServer.toString().concat(urlPath);
        }
    }
    
    public PPXClientImpl(URL ppxServer, String user, String pass){
        super(new MultiThreadedHttpConnectionManager());        
        this.ppxServer = ppxServer;
        this.user = user;
        this.pass = pass;
        this.credentials = Base64.encodeBase64String((this.user.concat(":").concat(this.pass)).getBytes());        
    }
    

    public Collection<Statement> annotate(AnnotateRequest annotateRequest) throws IOException, RequiredParameterMissingException, RDFParseException, RDFHandlerException{
        PostMethod method = this.getDefaultPostMethod(API_URL.API_ANNOTATE);        
                   method.setQueryString(annotateRequest.getNameValuePairs());
        int statusCode = super.executeMethod(method);
        if(statusCode!=200){
            throw new IOException();
        }        
        StatementCollector handler = new StatementCollector();
        RDFParser parser = Rio.createParser(RDFFormat.RDFXML);
                  parser.setRDFHandler(handler);
                  parser.parse(method.getResponseBodyAsStream(), "");
        return handler.getStatements();
    }
    
    public CategorizationResponse categorize(CategorizationRequest categorizationRequest) throws IOException, RequiredParameterMissingException{
        PostMethod method = this.getDefaultPostMethod(API_URL.API_CATEGORIZATION);        
                   method.setQueryString(categorizationRequest.getNameValuePairs());
        int statusCode = super.executeMethod(method);
        if(statusCode!=200){
            throw new IOException();
        }                
        return objectMapper.readValue(method.getResponseBodyAsStream(), CategorizationResponseImpl.class);
    }
        
    public ExtractResponse extract(ExtractRequest extractRequest) throws IOException, RequiredParameterMissingException{
        PostMethod method = this.getDefaultPostMethod(API_URL.API_EXTRACT);        
                   method.setQueryString(extractRequest.getNameValuePairs());
        int statusCode = super.executeMethod(method);
        if(statusCode!=200){
            throw new IOException();
        }                
        return objectMapper.readValue(method.getResponseBodyAsStream(), ExtractResponseImpl.class);
    }
    
    public SuggestResponse suggest(SuggestRequest suggestRequest) throws IOException, RequiredParameterMissingException{
        PostMethod method = this.getDefaultPostMethod(API_URL.API_SUGGEST);        
                   method.setQueryString(suggestRequest.getNameValuePairs());
        int statusCode = super.executeMethod(method);
        if(statusCode!=200){
            throw new IOException();
        }                      
        return objectMapper.readValue(method.getResponseBodyAsStream(), SuggestResponseImpl.class);
    }   
    
    public InputStream mapStream(MetaDataMappingRequest mapRequest) throws RequiredParameterMissingException, IOException
    {
        PostMethod method = this.getDefaultPostMethod(API_URL.API_MAP);  
         //method.setQueryString(mapRequest.getNameValuePairs());         
         //method.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
         NameValuePair[] pairs = mapRequest.getNameValuePairs();
         method.addParameters(pairs);
         

         int statusCode = super.executeMethod(method);
         if(statusCode!=200){
            System.out.println("Exited with code"+(new Integer(statusCode)).toString());
            throw new IOException();
            
         }           
         return method.getResponseBodyAsStream();
    }
    
    public MapResponse map(MetaDataMappingRequest mapRequest) throws RequiredParameterMissingException, IOException
    {
         PostMethod method = this.getDefaultPostMethod(API_URL.API_MAP);  
         //method.setQueryString(mapRequest.getNameValuePairs());         
         //method.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
         NameValuePair[] pairs = mapRequest.getNameValuePairs();
         method.addParameters(pairs);
         

         int statusCode = super.executeMethod(method);
         if(statusCode!=200){
            System.out.println("Return from server: "+(new Integer(statusCode)).toString());
            throw new IOException();
            
         }           
         return (new MapResponseImpl(method.getResponseBodyAsString()));                          
    }
    
    private PostMethod getDefaultPostMethod(API_URL apiSection){
        PostMethod method = new PostMethod(apiSection.getUrl(ppxServer));
                   method.addRequestHeader("Authorization", "Basic ".concat(credentials));
                   method.addRequestHeader("content-type", "application/x-www-form-urlencoded");                  
         return method;
    }    
}
