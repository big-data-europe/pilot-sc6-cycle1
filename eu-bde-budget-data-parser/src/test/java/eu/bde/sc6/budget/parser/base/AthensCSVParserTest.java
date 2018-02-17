package eu.bde.sc6.budget.parser.base;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import eu.bde.sc6.budget.parser.base.thessaloniki.CSVExpensesParser;
import eu.bde.sc6.budget.parser.impl.BudgetDataParserRegistryImpl;
import eu.bde.sc6.budget.parser.impl.LiteralMapperUsingSparql;
import eu.bde.virtuoso.utils.VirtuosoInserter;
import java.io.File;
import java.io.FileOutputStream;
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
import java.util.HashSet;
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
import org.openrdf.model.vocabulary.DCTERMS;
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
//public class AthensCSVParserTest extends VirtuosoCapability {
public class AthensCSVParserTest  {
    
    private static final String POOLPARTY_SERVER = "http://bde.poolparty.biz";
    private static final String POOLPARTY_PROJECT_NAME = "hierarchicalKAE";
    private static final String POOLPARTY_USER = "sc6admin";
    private static final String POOLPARTY_PASS = "sc6admin";
    private static final HashSet<String> LABELS_TO_SUBSTITUTE = new HashSet<>();
    

    static SPARQLRepository ppt = null;
    
    public AthensCSVParserTest() throws MalformedURLException {
    }
    
    @BeforeClass
            //https://bde.poolparty.biz/PoolParty/sparql/hierarchicalKAE
    public static void setUpClass() throws RepositoryException {
        ppt = new SPARQLRepository(POOLPARTY_SERVER.concat("/sparql").concat(POOLPARTY_PROJECT_NAME));
        ppt.initialize();
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
    //@Test
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
 
    //@Test
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
    
    @Test
    public void parseAllIncomeCSVs() throws IOException, UnknownBudgetDataParserException{
        
        LABELS_TO_SUBSTITUTE.add(DCTERMS.SUBJECT.toString());
        
        File rootDirectory = new File(AthensCSVParserTest.class.getClass().getResource("/athens/rdf/incomes/").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser(".*(athens/rdf/incomes).*");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                try {
                    if(file.toString().contains("2016")){
                        System.out.println("handling: " + file.getFileName().toString());
                        List<Statement> states = parser.transform(file.toString(), Files.readAllBytes(file));
                        LiteralMapperUsingSparql literalMapper = new LiteralMapperUsingSparql(
                                POOLPARTY_SERVER,
                                POOLPARTY_PROJECT_NAME,
                                POOLPARTY_USER,
                                POOLPARTY_PASS,                
                                LABELS_TO_SUBSTITUTE);                     
                        states = literalMapper.map(states);               
                        FileOutputStream fos = null;
                        RDFHandler fileWriter = null;
                        try {
                            fos = new FileOutputStream(new File("/home/turnguard/projects/swc/bde/resources/sc6/financial-ratios/11122017/athens/incomes/"+(file.getFileName().toString().replaceFirst("csv", "ttl"))));
                            fileWriter = Rio.createWriter(RDFFormat.TURTLE, fos);
                            fileWriter.startRDF();
                            for(Statement s : states){
                                fileWriter.handleStatement(s);
                            }
                            fileWriter.endRDF();
                        } catch (RDFHandlerException ex) {
                            ex.printStackTrace();
                        } finally {
                            if(fos!=null){
                                fos.close();
                            }
                        }                        
                    }                    
                } catch (TransformationException | RuntimeException ex) {
                    System.out.println("PROBLEMATIC FILE: " + file.toAbsolutePath()); 
                    ex.printStackTrace();
                //} catch (RDFHandlerException ex) {
                //    Logger.getLogger(ThessalonikiCSVParserTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    @Test
    public void parseAllExpensesCSVs() throws IOException, UnknownBudgetDataParserException{
        
        LABELS_TO_SUBSTITUTE.add(DCTERMS.SUBJECT.toString());
        
        File rootDirectory = new File(AthensCSVParserTest.class.getClass().getResource("/athens/rdf/expenses/").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser(".*(athens/rdf/expenses).*");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                try {
                    if(file.toString().contains("2016")){
                        System.out.println("handling: " + file.getFileName().toString());
                        List<Statement> states = parser.transform(file.toString(), Files.readAllBytes(file));
                        LiteralMapperUsingSparql literalMapper = new LiteralMapperUsingSparql(
                                POOLPARTY_SERVER,
                                POOLPARTY_PROJECT_NAME,
                                POOLPARTY_USER,
                                POOLPARTY_PASS,                
                                LABELS_TO_SUBSTITUTE);                     
                        states = literalMapper.map(states);               
                        FileOutputStream fos = null;
                        RDFHandler fileWriter = null;
                        try {
                            fos = new FileOutputStream(new File("/home/turnguard/projects/swc/bde/resources/sc6/financial-ratios/11122017/athens/expenses/"+(file.getFileName().toString().replaceFirst("csv", "ttl"))));
                            fileWriter = Rio.createWriter(RDFFormat.TURTLE, fos);
                            fileWriter.startRDF();
                            for(Statement s : states){
                                fileWriter.handleStatement(s);
                            }
                            fileWriter.endRDF();
                        } catch (RDFHandlerException ex) {
                            ex.printStackTrace();
                        } finally {
                            if(fos!=null){
                                fos.close();
                            }
                        }                        
                    }                    
                } catch (TransformationException | RuntimeException ex) {
                    System.out.println("PROBLEMATIC FILE: " + file.toAbsolutePath()); 
                    ex.printStackTrace();
                //} catch (RDFHandlerException ex) {
                //    Logger.getLogger(ThessalonikiCSVParserTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }    
        
}
