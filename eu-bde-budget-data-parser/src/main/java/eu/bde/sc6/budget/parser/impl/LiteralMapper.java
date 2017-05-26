package eu.bde.sc6.budget.parser.impl;

import biz.poolparty.ppx.client.api.request.RequiredParameterMissingException;
import biz.poolparty.ppx.client.api.response.metadata.mapping.MapResponse;
import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import biz.poolparty.ppx.client.impl.PPXClientImpl;
import biz.poolparty.ppx.client.impl.request.metadata.mapping.MetaDataMappingRequestImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.openrdf.model.Literal;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;
import org.openrdf.rio.ntriples.NTriplesWriter;

/**
 *  Given a list of statements, the map method returns
 * 
 * @author Victor Mireles - Semantic Web Company
 */
public class LiteralMapper {

    private final Stack<URI> predicateIndicatingLiterals = new Stack();
    private final Stack<URI> predicatesIndicatingSubstituions = new Stack();
    private final String format = "text/rdf+n3";        
    private String replaceMode = "object";
    private String mappingMode = "label";
    private MetaDataMappingRequestImpl request;
    private PPXClientImpl client;
    

    
    public LiteralMapper(String PPURL,
            String PPUser,
            String PPPass,
            String PPProject,
            String PPConceptScheme) throws MalformedURLException {
        /*
        This constructor can in the future be used for different mapping options for
        each parser
        */
        this.predicateIndicatingLiterals.add(new URIImpl("http://purl.org/dc/terms/subject"));
        this.predicatesIndicatingSubstituions.add(new URIImpl("http://www.w3.org/2004/02/skos/core#hiddenLabel"));
        this.predicatesIndicatingSubstituions.add(new URIImpl("http://www.w3.org/2004/02/skos/core#prefLabel"));
        this.predicatesIndicatingSubstituions.add(new URIImpl("http://www.w3.org/2004/02/skos/core#altLabel"));

        
        this.client = new PPXClientImpl(
                new URL(PPURL), PPUser, PPPass);
        MetaDataMappingRequestImpl mapRequest = new MetaDataMappingRequestImpl(PPProject,
                "" ,
                format ,
                mappingMode,
                predicateIndicatingLiterals.pop().stringValue(),
                PPConceptScheme);
        
        
        mapRequest.setReplaceMode(replaceMode);
        String[] la = {"en","el","none"};
        //mapRequest.setLanguages(la);
        
        
        //Other properties, all to the same concept scheme
        while (!predicateIndicatingLiterals.empty())
        {
            mapRequest.addRdfProperty(predicateIndicatingLiterals.pop().stringValue(), PPConceptScheme);
        }
        
        while(!predicatesIndicatingSubstituions.empty())
        {
            mapRequest.addConceptInfo(predicatesIndicatingSubstituions.pop().stringValue());
        }
        this.request = mapRequest;
        
    }
    public List<Statement> map(List<Statement> data) throws RDFHandlerException, UnsupportedEncodingException 
    {

        RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
        LinkedList<Statement> returnData = new LinkedList<>();
        StatementCollector collector = new StatementCollector(returnData);
        rdfParser.setRDFHandler(collector);
        System.out.println("Loading statements:  "+(new Integer(data.size())).toString());
        

        // First create the request.
        // We add each statement from the list into an OutputString, then set that 
        // Stream's toString() as the statements of our request
        int i = 0;        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        NTriplesWriter w = new NTriplesWriter(baos);
        
        RDFFormat format = RDFFormat.NTRIPLES;
        Collection <String> mimeTypes = format.getMIMETypes();
        Charset X = Charset.forName("UTF-16");
        Collection<String> fileExtensions = format.getFileExtensions();
        boolean supportsNamespaces = format.supportsNamespaces();
        boolean supportsContexts = format.supportsContexts();
                

        int discardBefore = 0;
        int discardAfter  = 2200;
        try { 
            int numDataPoints = data.size();
            w.startRDF();
            for (Statement S : data)
            {
                i++;
                if ((i<discardBefore) || (i>discardAfter)) {
                    continue;
                }       
                Statement x;
                Value S1 = S.getObject();

                if (S1.getClass().getCanonicalName()==LiteralImpl.class.getCanonicalName() )
                        {            

                            String newObj = S1.toString().replace("\"","")+" ";//
                            String objNorm = Normalizer.normalize(newObj, Normalizer.Form.NFD);
                            String objPure = StringUtils.stripAccents(objNorm.toLowerCase());
                            //objPure = objPure.substring(0, objPure.length()-1);
                            x = new StatementImpl(S.getSubject(),S.getPredicate(),new LiteralImpl(objPure) );
                            w.handleStatement(x);
                            System.out.println(objPure+"*");
                        }
                else
                {
                    x  = S;

                }
                w.handleStatement(x);

                //System.out.println(x.toString());

                if (i % 10000 == 10)
                {
                    int len = baos.size();
                    System.out.println((new Integer(i)).toString()+" "+(new Integer(len)).toString());                
                }                        
            }
            w.endRDF();
            this.submitAndParsePart(rdfParser, w, baos);
            return (List) collector.getStatements();
        
        } catch (IOException |  RequiredParameterMissingException |  RDFParseException | RDFHandlerException ex) {
            Logger.getLogger(LiteralMapper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return (data);
        }
        
        
    }
    
    
    void submitAndParsePart(RDFParser rdfParser,NTriplesWriter w,ByteArrayOutputStream baos) throws IOException, RDFParseException, RDFParseException, RDFHandlerException, RequiredParameterMissingException
    {
         String statementString = Normalizer.normalize(baos.toString(), Normalizer.Form.NFD);
         this.request.setStatements(statementString);         
        //System.out.println((new Integer(i-discardBefore)).toString()+"  Statements loaded ----------------------------\n\n");

        // Send the resquest and parse the resulting stream           
        System.out.println(statementString);
        System.out.println("\n--Mapping (PPX): ");
        MapResponse response = this.client.map(this.request);
        System.out.println("\n--Got server response ->\n");
        StringBuilder sbuilder = new StringBuilder();
        for (String st : response.getStatements())
        {
            String st2 = st.replace("> a <","> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <");                
            sbuilder.append(st2+"\n");
            //System.out.println(st2);
        }
        //System.out.println("\n--------------------\n");
        rdfParser.parse(new ByteArrayInputStream(sbuilder.toString().getBytes()), "");
    }
    
}
