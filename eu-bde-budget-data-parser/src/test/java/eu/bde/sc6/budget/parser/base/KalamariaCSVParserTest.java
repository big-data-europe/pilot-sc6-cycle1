 package eu.bde.sc6.budget.parser.base;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import eu.bde.sc6.budget.parser.base.thessaloniki.CSVExpensesParser;
import eu.bde.sc6.budget.parser.impl.BudgetDataParserRegistryImpl;
import eu.bde.sc6.budget.parser.impl.LiteralMapperUsingSparql;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.Rio;

/**
 *
 * @author turnguard
 */
//public class KalamariaCSVParserTest  extends VirtuosoCapability  {
 public class KalamariaCSVParserTest  {
    
    public KalamariaCSVParserTest() throws MalformedURLException {
        super();
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

    private static final String POOLPARTY_SERVER = "http://bde.poolparty.biz";
    private static final String POOLPARTY_PROJECT_NAME = "hierarchicalKAE";
    private static final String POOLPARTY_USER = "sc6admin";
    private static final String POOLPARTY_PASS = "sc6admin";
    private static final HashSet<String> LABELS_TO_SUBSTITUTE = new HashSet<>();
        
            
    //@Test
    public void testParseSimpleIncomeFile() throws UnknownBudgetDataParserException, IOException, TransformationException{
        
        LABELS_TO_SUBSTITUTE.add(DCTERMS.SUBJECT.toString());
        
        String fileName = "2016_06_30_21.50.csv";
        byte[] file = IOUtils.toByteArray(KalamariaCSVParserTest.class.getResourceAsStream("/kalamaria/csv/incomes/".concat(fileName)));
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser(".*(kalamaria/csv/incomes).*");
        List<Statement> states = parser.transform(fileName, file);
        LiteralMapperUsingSparql literalMapper = new LiteralMapperUsingSparql(
                POOLPARTY_SERVER,
                POOLPARTY_PROJECT_NAME,
                POOLPARTY_USER,
                POOLPARTY_PASS,                
                LABELS_TO_SUBSTITUTE);                     
        states = literalMapper.map(states);
        states.forEach(System.out::println);
        FileOutputStream fos = null;
        RDFHandler fileWriter = null;
        try {
            fos = new FileOutputStream(new File("/home/turnguard/Downloads/"+(fileName.replaceFirst("csv", "ttl"))));
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
    
    //@Test
    public void testParseSimpleExpenseFile() throws UnknownBudgetDataParserException, IOException, TransformationException{
        
        LABELS_TO_SUBSTITUTE.add(DCTERMS.SUBJECT.toString());
        
        String fileName = "2016_06_30_21.51.csv";
        byte[] file = IOUtils.toByteArray(KalamariaCSVParserTest.class.getResourceAsStream("/kalamaria/csv/expenses/".concat(fileName)));
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser(".*(kalamaria/csv/expenses).*");
        List<Statement> states = parser.transform(fileName, file);
        LiteralMapperUsingSparql literalMapper = new LiteralMapperUsingSparql(
                POOLPARTY_SERVER,
                POOLPARTY_PROJECT_NAME,
                POOLPARTY_USER,
                POOLPARTY_PASS,                
                LABELS_TO_SUBSTITUTE);                     
        states = literalMapper.map(states);
        states.forEach(System.out::println);
        FileOutputStream fos = null;
        RDFHandler fileWriter = null;
        try {
            fos = new FileOutputStream(new File("/home/turnguard/Downloads/"+(fileName.replaceFirst("csv", "ttl"))));
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
    
    //@Test
    public void parseAllIncomeCSVs() throws IOException, UnknownBudgetDataParserException{
        
        LABELS_TO_SUBSTITUTE.add(DCTERMS.SUBJECT.toString());
        
        File rootDirectory = new File(KalamariaCSVParserTest.class.getClass().getResource("/kalamaria/csv/incomes/").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser(".*(kalamaria/csv/incomes).*");
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
                            fos = new FileOutputStream(new File("/home/turnguard/projects/swc/bde/resources/sc6/financial-ratios/24082017/kalamaria/incomes/"+(file.getFileName().toString().replaceFirst("csv", "ttl"))));
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
    public void parseAllExpenseCSVs() throws IOException, UnknownBudgetDataParserException{
        
        LABELS_TO_SUBSTITUTE.add(DCTERMS.SUBJECT.toString());
        
        File rootDirectory = new File(KalamariaCSVParserTest.class.getClass().getResource("/kalamaria/csv/expenses/2016/06June").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser(".*(kalamaria/csv/expenses).*");
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
                        File root = new File("/home/turnguard/projects/swc/bde/resources/sc6/financial-ratios/11122017/kalamaria/expenses/");
                        if(!root.exists()){
                            root.mkdirs();
                        }
                        try {
                            fos = new FileOutputStream(new File(root,(file.getFileName().toString().replaceFirst("csv", "ttl"))));
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
    
    //@Test
    public void testAllIncomeCSVsForDataErrors() throws IOException{
        
        File rootDirectory = new File(KalamariaCSVParserTest.class.getClass().getResource("/kalamaria/csv/incomes/").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = new eu.bde.sc6.budget.parser.base.kalamaria.CSVIncomesParser();
        
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
                    ex.printStackTrace();
                //} catch (RDFHandlerException ex) {
                //    Logger.getLogger(ThessalonikiCSVParserTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    //@Test
    public void testAllExpensesCSVsForDataErrors() throws IOException{
        
        File rootDirectory = new File(KalamariaCSVParserTest.class.getClass().getResource("/kalamaria/csv/expenses/").getFile());
        Path path = Paths.get(rootDirectory.toURI());
        
        BudgetDataParser parser = new eu.bde.sc6.budget.parser.base.kalamaria.CSVExpensesParser();
        
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
                //    Logger.getLogger(ThessalonikiCSVParserTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
}
