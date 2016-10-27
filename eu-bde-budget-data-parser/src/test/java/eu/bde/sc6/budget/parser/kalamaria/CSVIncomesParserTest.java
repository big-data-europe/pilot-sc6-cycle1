package eu.bde.sc6.budget.parser.kalamaria;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import eu.bde.sc6.budget.parser.base.thessaloniki.CSVExpensesParser;
import eu.bde.sc6.budget.parser.base.thessaloniki.CSVIncomesParser;
import eu.bde.sc6.budget.parser.impl.BudgetDataParserRegistryImpl;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;

/**
 *
 * @author turnguard
 */
public class CSVIncomesParserTest {
    
    public CSVIncomesParserTest() {
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
    
    //@Test
    public void testParseSimpleFile() throws UnknownBudgetDataParserException, IOException, TransformationException{
        String fileName = "2016_06_30_21.50.csv";
        byte[] file = IOUtils.toByteArray(CSVIncomesParserTest.class.getResourceAsStream("/kalamaria/incomes/".concat(fileName)));
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser(".*(kalamaria/csv/incomes).*");
        parser.transform(fileName, file).forEach( s -> {
            System.out.println(s);
        });
    }
    
    @Test
    public void testAllIncomeCSVsForDataErrors() throws IOException{
        String rootDirectory = "/home/turnguard/projects/swc/bde/workspace_bde/pilot-sc6-cycle1/eu-bde-budget-data-parser/src/test/resources/kalamaria/incomes/";
        
        Path path = FileSystems.getDefault().getPath(rootDirectory);
        
        BudgetDataParser parser = new eu.bde.sc6.budget.parser.base.kalamaria.CSVIncomesParser();
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                try {
                    List<Statement> states = parser.transform(file.toString(), Files.readAllBytes(file));
                    System.out.println(file.toString() + " " + states.size());
                } catch (TransformationException | RuntimeException ex) {
                    System.out.println("PROBLEMATIC FILE: " + file.toAbsolutePath());
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    //@Test
    public void testAllExpensesCSVsForDataErrors() throws IOException{
        String rootDirectory = "/home/turnguard/projects/swc/bde/workspace_bde/pilot-sc6-cycle1/eu-bde-sc6-parser/Input CSV/Expenses";
        
        Path path = FileSystems.getDefault().getPath(rootDirectory);
        
        BudgetDataParser parser = new CSVExpensesParser();
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                try {
                    List<Statement> states = parser.transform(file.toString(), Files.readAllBytes(file));
                    System.out.println(file.toString() + " " + states.size());
                } catch (TransformationException ex) {
                    System.out.println("PROBLEMATIC FILE: " + file.toAbsolutePath());
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    
}
