package eu.bde.sc6.budget.parser.base.athens;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.vocabulary.ELOD;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;
import org.openrdf.rio.rdfxml.RDFXMLParser;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class CSVExpensesParser implements BudgetDataParser {
    
    private final static String IDENTIFIER = ".*(athens/rdf/expenses).*";
    private final static Pattern FILENAME_DATE_PATTERN = Pattern.compile("([0-9]{4})_([0-9]{2})_([0-9]{2}).*");
    private final static String INSTANCE_NAMESPACE = "http://linkedeconomy.org/resource/athens/expenses/";
    
    /**
     * athens budget data comes as is in rdf/xml
     * @param fileName
     * @param file
     * @return
     * @throws TransformationException 
     */
    private URI fixURI(URI u){
        if(
            u.stringValue().startsWith("http://linkedeconomy.org/resource/UnitPriceSpecification") ||
            u.stringValue().startsWith("http://linkedeconomy.org/resource/RevenueRecognizedItem") ||
            u.stringValue().startsWith("http://linkedeconomy.org/resource/BudgetItem") ||    
            u.stringValue().startsWith("http://linkedeconomy.org/resource/CollectedItem") ||
            u.stringValue().startsWith("http://linkedeconomy.org/resource/SpendingItem") ||
            u.stringValue().startsWith("http://linkedeconomy.org/resource/CommittedItem") ||
            u.stringValue().startsWith("http://linkedeconomy.org/resource/ExpenseApprovalItem") ||
            u.stringValue().startsWith("http://linkedeconomy.org/resource/ExpenditureLine")
        ){
            return new URIImpl(
                u.stringValue().replaceFirst(
                        "http://linkedeconomy.org/resource/", 
                        INSTANCE_NAMESPACE
                )
            );
        }
        return u;
    }
    @Override
    public List<Statement> transform(String fileName, byte[] file) throws TransformationException {
        Literal source = new LiteralImpl(fileName);
        
        StatementCollector collector = new StatementCollector(){
            @Override
            public void handleStatement(Statement st) {
                Value o = st.getObject();
                
                if(st.getPredicate().stringValue().equals("http://linkedeconomy.org/ontology#buyer")){
                    o = new URIImpl("http://bde.poolparty.biz/hierarchicalKAE/669");                 
                }
                URI s = fixURI((URI)st.getSubject());
                if(o instanceof URI){
                    o = fixURI((URI)o);
                }     
                if(st.getPredicate().equals(DCTERMS.ISSUED)){
                    o = new LiteralImpl(st.getObject().stringValue(), XMLSchema.DATE);
                }

                super.handleStatement(new StatementImpl(s, st.getPredicate(), o));
                
                if(st.getPredicate().equals(RDF.TYPE)){
                    super.handleStatement(new StatementImpl(s,DCTERMS.SOURCE,source));
                }
            }
        };                  
        InputStream inStream = null;
        RDFParser p = new RDFXMLParser(){
            @Override
            protected Literal createLiteral(String label, String lang, URI datatype) throws RDFParseException {
                // fixing wrong xsd:dateTimes [ for now only as string ]
                if(datatype.equals(XMLSchema.DATETIME)){
                    return super.createLiteral(label, null, null);
                } else {
                    return super.createLiteral(label, lang, datatype);
                }
            }            
        };
        p.setRDFHandler(collector);
        try {
            inStream = new ByteArrayInputStream(file);
            p.parse(inStream, fileName);
        } catch( IOException | RDFParseException | RDFHandlerException ioex){
            throw new TransformationException(ioex);
        } finally {
            if(inStream!=null){
                try {
                    inStream.close();
                } catch (IOException ex) {}
            }
        }
        return 
                collector.getStatements() instanceof List
                ? 
                (List)collector.getStatements()
                :
                new ArrayList(collector.getStatements());
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public boolean canHandleByFileName(String fileName) {
        return fileName.matches(IDENTIFIER);
    }
}
