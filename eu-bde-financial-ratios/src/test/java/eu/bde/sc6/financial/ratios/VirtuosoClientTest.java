package eu.bde.sc6.financial.ratios;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResultHandlerBase;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author turnguard
 */
public class VirtuosoClientTest {
    
    public VirtuosoClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void virtuosoRepository() throws RepositoryException, MalformedQueryException, QueryEvaluationException, TupleQueryResultHandlerException{
        SPARQLRepository repository = new SPARQLRepository(
                "https://bde-virtuoso.poolparty.biz/sparql"
        );
        repository.initialize();
        RepositoryConnection repCon = null;
        try {
            repCon = repository.getConnection();
            repCon
                .prepareTupleQuery(
                    QueryLanguage.SPARQL, 
                    "SELECT * FROM <urn:debug:sc6> WHERE { ?s ?p ?o } LIMIT 100")
                        .evaluate(new TupleQueryResultHandlerBase(){
                            @Override
                            public void handleSolution(BindingSet bindingSet) throws TupleQueryResultHandlerException {
                                System.out.println(bindingSet);
                            }
                        });
        } finally {
            if(repCon!=null){
                repCon.close();
            }
        }
    }
}
