package biz.poolparty.ppx.client.impl.request.metadata.mapping;

import biz.poolparty.ppx.client.api.request.Param;
import biz.poolparty.ppx.client.api.request.metadata.mapping.MetaDataMappingRequest;
import biz.poolparty.ppx.client.base.request.PPXRequestBase;
import java.util.Arrays;
import java.util.LinkedList;
import org.apache.commons.httpclient.NameValuePair;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class MetaDataMappingRequestImpl extends PPXRequestBase implements MetaDataMappingRequest {
    //Mandatory
    private final Param<String> format = new Param<>("format",true);
    public final Param<String> statements = new Param<>("statements",true);    
    private final Param<String> projectName = new Param<>("projectName",true);    
    private final Param<String> mappingMode = new Param<>("mappingMode",true);    
    private final LinkedList<Param<String>> rdfPropertiesProperty = new LinkedList<>();
    private final LinkedList<Param<String>> rdfPropertiesConceptScheme = new LinkedList<>();
    
    //Optional
    private final LinkedList<Param<String>> conceptInfo = new  LinkedList<>();
    private final Param<String> languages = new Param<>("languages","",false);
    private final Param<String> replaceMode = new Param<>("replaceMode","object",false);
    private final Param<String> thesaurusNameSpace = new Param<>("thesaurusNameSpace",false);    
    private final Param<String> thesaurusUri = new Param<>("thesaurusUri",false);    
   
    public MetaDataMappingRequestImpl(String projectName,String statements, String format, String mappingMode,String property, String scheme){        
        this.statements.setValue(statements);
        this.projectName.setValue(projectName);
        this.format.setValue(format);
        this.mappingMode.setValue(mappingMode);               
        this.addRdfProperty(property,scheme);
  
    }

    public void addStatement(String statement)
    {
        String currentStatements = this.statements.getValue();
        this.statements.setValue(currentStatements+"\n"+statement);        
    }
    
    public void setFormat(String format){
        this.format.setValue(format);
    }    
    public void setStatements(String statements){
        this.statements.setValue(statements);
    }
    public void setProjectName(String projectName){
        this.projectName.setValue(projectName);
    }    
    public void setMappingMode(String mappingMode){
        this.mappingMode.setValue(mappingMode);
    }    
    public void setLanguages(String languages){
        this.languages.setValue(languages);
    }
    public void setReplaceMode(String replaceMode){
        this.replaceMode.setValue(replaceMode);
    }
    public void setThesaurusNameSpace(String thesaurusNameSpace){
        this.thesaurusNameSpace.setValue(thesaurusNameSpace);
    }    
    public void setThesaurusUri(String thesaurusUri){
        this.thesaurusUri.setValue(thesaurusUri);
    } 
    public void addConceptInfo(String predicate)
    {
       String numNextConceptInfo = (new Integer(this.conceptInfo.size())).toString();
       String nextConceptName = "conceptInfo["+numNextConceptInfo+"]";
       Param<String> newConceptInfo = new Param<String>(nextConceptName,false);
       newConceptInfo.setValue(predicate);
       this.conceptInfo.add(newConceptInfo);        
    }
    
    public void addRdfProperty(String property, String scheme)
    {
       String numNextRdfProperty;
       numNextRdfProperty = (new Integer(this.rdfPropertiesProperty.size())).toString();
                    
       Param<String> rdfProperty0Prop = new Param<String>("rdfProperties["+numNextRdfProperty+"].rdfProperty",false);
       rdfProperty0Prop.setValue(property);       
       this.rdfPropertiesProperty.add(rdfProperty0Prop);               
        
       Param<String> rdfProperty0Scheme = new Param<String>("rdfProperties["+numNextRdfProperty+"].conceptscheme[0]",false);
       rdfProperty0Scheme.setValue(scheme);       
       this.rdfPropertiesConceptScheme.add(rdfProperty0Scheme);     
       
    }
    
    
  
    
    
}
