package eu.bde.sc6.budget.parser.base;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import eu.bde.sc6.budget.parser.base.thessaloniki.CSVExpensesParser;
import eu.bde.sc6.budget.parser.impl.BudgetDataParserRegistryImpl;
import eu.bde.virtuoso.utils.VirtuosoInserter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.rio.RDFHandlerException;

/**
 *
 * @author turnguard
 */
//public class AthensCSVParserTest extends VirtuosoCapability {
public class AthensCSVParserTest  {
    
    public AthensCSVParserTest() throws MalformedURLException {
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

    //@Test
    public void testAvailableInRegistry() throws UnknownBudgetDataParserException {
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser("thessaloniki.incomes.csv");
        assertNotNull(parser);
    }
    @Test
    public void testAvailableInRegistry001() throws UnknownBudgetDataParserException {
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParserForFileName("/var/lib/bde/flume/sc6/budgets/thessaloniki/csv/incomes/2016/06June/2016_06_21_21.29.csv");
        assertNotNull(parser);
    }    
        
    //@Test
    public void testAllIncomeCSVsForDataErrors() throws IOException{
        
        File rootDirectory = new File(AthensCSVParserTest.class.getClass().getResource("/athens/rdf/incomes/").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = new eu.bde.sc6.budget.parser.base.athens.CSVIncomesParser();
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                try {
                    if(file.toString().contains("2016")){ 
                        List<Statement> states = parser.transform(file.toString(), Files.readAllBytes(file));
                        System.out.println(file.toString() + " " + states.size());
                        //uploadToVirtuoso(states);                                    
                    }
                } catch (TransformationException | RuntimeException ex) {
                    System.out.println("PROBLEMATIC FILE: " + file.toAbsolutePath());                    
                //} catch (RDFHandlerException ex) {
                //    Logger.getLogger(AthensCSVParserTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    //@Test
    public void testAllExpensesCSVsForDataErrors() throws IOException{
        
        File rootDirectory = new File(AthensCSVParserTest.class.getClass().getResource("/athens/rdf/expenses/").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = new eu.bde.sc6.budget.parser.base.athens.CSVExpensesParser();
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                try {
                    if(file.toString().contains("2016")){                       
                        List<Statement> states = parser.transform(file.toString(), Files.readAllBytes(file));
                        System.out.println(file.toString() + " " + states.size());
                        //uploadToVirtuoso(states);                                    
                    }
                } catch (TransformationException | RuntimeException ex) {
                    System.out.println("PROBLEMATIC FILE: " + file.toAbsolutePath());                    
                //} catch (RDFHandlerException ex) {
                //    Logger.getLogger(AthensCSVParserTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
 
    @Test
    public void testSingleExpensesCSVsForDataErrors() throws IOException{
        
        File rootDirectory = new File(AthensCSVParserTest.class.getClass().getResource("/athens/rdf/expenses/").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = new eu.bde.sc6.budget.parser.base.athens.CSVExpensesParser();
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                try {
                    if(file.toString().contains("2016") && file.toString().contains("jun")){                       
                        List<Statement> states = parser.transform(file.toString(), Files.readAllBytes(file));
                        
                        VirtuosoInserter handler = new VirtuosoInserter(
                                new URL("http://bde-virtuoso.poolparty.biz/sparql-graph-crud-auth"), 
                                new URIImpl("urn:sc6"), 
                                "dba", 
                                "egiaCivahY6z"
                        );
                        //handler.startRDF();
                        for(Statement state : states){
                          //  handler.handleStatement(state);
                            System.out.println(state);
                        }                        
                        //handler.endRDF();
                        System.out.println(file.toString() + " " + states.size());
                        //uploadToVirtuoso(states);                                    
                    }
                } catch (TransformationException | RuntimeException ex) {
                    System.out.println("PROBLEMATIC FILE: " + file.toAbsolutePath());                    
                }
                //catch (RDFHandlerException ex) {
                //    ex.printStackTrace();
                //    System.out.println("PROBLEMATIC FILE: " + file.toAbsolutePath());                    
                //}
                return FileVisitResult.CONTINUE;
            }
        });
    }    
}
