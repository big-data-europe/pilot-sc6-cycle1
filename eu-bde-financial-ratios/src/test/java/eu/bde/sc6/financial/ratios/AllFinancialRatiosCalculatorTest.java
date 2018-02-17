package eu.bde.sc6.financial.ratios;

import static eu.bde.sc6.financial.ratios.CalculatorsTest.dataRepository;
import eu.bde.sc6.financial.ratios.api.CalculationException;
import eu.bde.sc6.financial.ratios.api.FinancialRatioCalculatorFactory;
import eu.bde.sc6.financial.ratios.impl.FinancialRatioCalculatorRegistryImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.Rio;

/**
 *
 * @author turnguard
 */
public class AllFinancialRatiosCalculatorTest {
static SPARQLRepository dataRepository = new SPARQLRepository(
            //"https://bde-virtuoso.poolparty.biz/sparql"      
            "http://localhost:8890/sparql"      
    );
    static SPARQLRepository pptRepository = new SPARQLRepository(
            "http://bde.poolparty.biz/PoolParty/sparql/hierarchicalKAE"      
    );    
    static URI DATA_GRAPH = new URIImpl("urn:sc6:data");
    static URI BUDGET = new URIImpl("http://bde.poolparty.biz/hierarchicalKAE/667");
    static Literal BUDGET_LABEL = new LiteralImpl("Kalamaria");
    static URI BUYER_SELLER_KALAMARIA = new URIImpl("http://bde.poolparty.biz/hierarchicalKAE/664");
    static Literal ISSUED = new LiteralImpl("2016-06-30T12:00:00+02:00", XMLSchema.DATETIME);
        
    public AllFinancialRatiosCalculatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws RepositoryException {
        dataRepository.initialize();
        pptRepository.initialize();
        FinancialRatioCalculatorRegistryImpl.setDataRepository(dataRepository);
        FinancialRatioCalculatorRegistryImpl.setPPTRepository(pptRepository);
    }
    
    @AfterClass
    public static void tearDownClass() throws RepositoryException {
        dataRepository.shutDown();
        pptRepository.shutDown();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    //@Test
    public void testKalamariaFinancialRatioCalculation(){
        FinancialRatioCalculatorRegistryImpl.getInstance().iterator().forEachRemaining(frcf -> {
            System.out.println(frcf.getClass().getName());
            try {
                frcf
                        .get(dataRepository, pptRepository)
                        .calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED)
                        .forEach(System.out::println);
            } catch (CalculationException ex) {
                ex.printStackTrace();
            }
        });        
    }
    
    
    public void testWriteFinancialRatioCalculationSingleDate(
            Literal issued,
            URI municipality,
            Literal municipalityLabel,
            URI budget,
            Literal budgetLabel
    ) throws FileNotFoundException, RDFHandlerException {
        FileOutputStream fos = null;
        RDFHandler handler = null;
        File target = new File(
                (
                "/home/turnguard/projects/swc/bde/resources/sc6/financial-ratios/11122017/"+municipalityLabel.stringValue().toLowerCase()+"/financial-ratios/"
                    .concat(issued.stringValue().split("\\+")[0])
                    .concat(".ttl")
                )
                );
        if(!target.getParentFile().exists()){
            target.getParentFile().mkdirs();
        }
        try {
            fos = new FileOutputStream(
                    target
            );
            handler = Rio.createWriter(RDFFormat.TURTLE, fos);
            handler.startRDF();
            for(Iterator<FinancialRatioCalculatorFactory> iter = FinancialRatioCalculatorRegistryImpl.getInstance().iterator(); iter.hasNext(); ){
                FinancialRatioCalculatorFactory frcf = iter.next();
                System.out.println(frcf.getClass().getName());
                try {
                    List<Statement> states = frcf
                            .get(dataRepository, pptRepository)
                            .calculate(DATA_GRAPH, budget, budgetLabel, municipality, issued);
                    for(Statement state : states){
                        handler.handleStatement(state);
                    } 
                } catch (CalculationException ex) {
                    ex.printStackTrace();
                }            
            }
            handler.endRDF();
        } finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException ex) {}
            }
        }
    }    
    
    @Test
    public void testCreateFinancialRatiosPerBuyerSellerForAllDates() throws RepositoryException, MalformedQueryException, QueryEvaluationException, FileNotFoundException, RDFHandlerException{
        String BUYER_SELLER_PER_DATE = ""
                + "SELECT DISTINCT ?municipality (xsd:date(?issued) AS ?date) FROM <urn:sc6:data> WHERE { " 
                + " ?s <http://linkedeconomy.org/ontology#buyer>|<http://linkedeconomy.org/ontology#seller> ?municipality; " 
                + "    <http://purl.org/dc/terms/issued> ?issued "
                // ONLY thessaloniki
                //+ "FILTER(?municipality=<http://bde.poolparty.biz/hierarchicalKAE/665>) "
                // ONLY kalamaria
                //+ "FILTER(?municipality=<http://bde.poolparty.biz/hierarchicalKAE/664>) "
                // ONLY athens
                //+ "FILTER(?municipality=<http://bde.poolparty.biz/hierarchicalKAE/669>) "
                + "}";
        RepositoryConnection dataCon = null;
        TupleQueryResult result = null;
        try {
            dataCon = dataRepository.getConnection();
            result = dataCon.prepareTupleQuery(QueryLanguage.SPARQL, BUYER_SELLER_PER_DATE).evaluate();
            while(result.hasNext()){
                BindingSet bindingSet = result.next();
                
                BindingSet budgetInfo = getBudgetInfo((URI)bindingSet.getValue("municipality"));
                Literal issued = (Literal)bindingSet.getValue("date");
                URI municipality = (URI)budgetInfo.getValue("municipality");
                Literal municipalityLabel = (Literal)budgetInfo.getValue("municipalityLabel");
                URI budget = (URI)budgetInfo.getValue("budget");
                Literal budgetLabel = (Literal)budgetInfo.getValue("budgetLabel");
                System.out.println(issued);
                System.out.println(municipality);
                System.out.println(municipalityLabel);
                System.out.println(budget);
                System.out.println(budgetLabel);
                this.testWriteFinancialRatioCalculationSingleDate(issued, municipality, municipalityLabel, budget, budgetLabel);
            }
        } finally {
            if(dataCon!=null){
                dataCon.close();
            }
        }
    }
    
    private BindingSet getBudgetInfo(URI municipality) throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        String MUNICIPALITY_QUERY = ""
                + "PREFIX skos:<http://www.w3.org/2004/02/skos/core#>"
                + "SELECT ?municipality ?municipalityLabel ?budget ?budgetLabel WHERE {"
                + " ?municipality skos:prefLabel ?municipalityLabel;"
                + "               skos:related ?budget . "
                + " ?budget skos:prefLabel ?budgetLabel "
                + " FILTER(?municipality=<<municipality>>)"
                + "}";
        RepositoryConnection pptCon = null;
        TupleQueryResult result = null;
        String queryMunicipality = MUNICIPALITY_QUERY.replaceFirst("<municipality>", municipality.stringValue());
        
        try {
            pptCon = pptRepository.getConnection();
            result = pptCon.prepareTupleQuery(QueryLanguage.SPARQL, queryMunicipality).evaluate();
            return result.next();
        } finally {
            if(pptCon!=null){
                pptCon.close();
            }
        }
    }
}
