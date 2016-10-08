package eu.bde.sc6.budget.parser;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.BudgetDataParserRegistry;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import eu.bde.sc6.budget.parser.base.thessaloniki.CSVExpensesParser;
import eu.bde.sc6.budget.parser.base.thessaloniki.CSVIncomesParser;
import eu.bde.sc6.budget.parser.impl.BudgetDataParserRegistryImpl;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.ValueFactoryImpl;

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
        String fileName = "2016_06_30_21.29.csv";
        byte[] file = IOUtils.toByteArray(CSVIncomesParserTest.class.getResourceAsStream("/thessaloniki/incomes/".concat(fileName)));
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser("thessaloniki.incomes.csv");        
        parser.transform(fileName, file).forEach( s -> {
            System.out.println(s);
        });
    }
    
    //@Test
    public void testDateParsingFromFileName(){
        Pattern FILENAME_DATE_PATTERN = Pattern.compile("([0-9]{4})_([0-9]{2})_([0-9]{2}).*");
        String fileName = "2016_06_30_21.29.csv";
        Matcher matcher = FILENAME_DATE_PATTERN.matcher(fileName);
        if(matcher.matches()){
            System.out.println("year:"+matcher.group(1));
            System.out.println("month:"+matcher.group(2));
            System.out.println("day:"+matcher.group(3));
        } else {
            System.out.println("doesn't match");
        }
    }
    
    //@Test
    public void testCreateDateTimeLiteral(){
        Pattern FILENAME_DATE_PATTERN = Pattern.compile("([0-9]{4})_([0-9]{2})_([0-9]{2}).*");
        String fileName = "2016_06_30_21.29.csv";
        Matcher matcher = FILENAME_DATE_PATTERN.matcher(fileName);
        if(matcher.matches()){
            System.out.println("year:"+matcher.group(1));
            System.out.println("month:"+matcher.group(2) + " " + Integer.parseInt(matcher.group(2)));
            System.out.println("day:"+matcher.group(3));
            
            Calendar mydate = new GregorianCalendar();
            
            mydate.set(
                    Integer.parseInt(matcher.group(1)), 
                    Integer.parseInt(matcher.group(2))-1, 
                    Integer.parseInt(matcher.group(3)),
                    12,
                    0,
                    0
            );
            
            System.out.println(ValueFactoryImpl.getInstance().createLiteral(mydate.getTime()));
        }
    }
    
    //@Test
    public void testCanHandleByFileName(){
        System.err.println("/var/lib/bde/flume/sc6/thessaloniki/csv/incomes/2016_06_30_21.31.csv".matches(".*(xthessaloniki/csv/incomes).*"));
    }
    
    //@Test
    public void testCanHandleByFileNameExpenses(){
        BudgetDataParser parser = new CSVExpensesParser();
        System.out.println("canHandlerByFileName:expenses:"+parser.canHandleByFileName("/var/lib/bde/flume/sc6/thessaloniki/csv/expenses/2015/08August/2015_08_13_23.00.csv"));
    }    
    
    //@Test
    public void testGetFileNameFromAbsolutePath(){
        String absPath = "/var/lib/bde/flume/sc6/thessaloniki/csv/expenses/2015/08August/2015_08_13_23.00.csv";
        Path path = FileSystems.getDefault().getPath(absPath);
        System.out.println("path.getFileName():"+path.getFileName());
        System.out.println("path.getNameCount():"+path.getNameCount());
        System.out.println("path.getParent():"+path.getParent());
        System.out.println("path.getRoot():"+path.getRoot());
        System.out.println("path.toFile().getAbsolutePath():"+path.toFile().getAbsolutePath());
        System.out.println("path.toFile().getName():"+path.toFile().getName());
        File spoolDirectory = new File("/var/lib/bde/flume/sc6/thessaloniki/csv/");
        System.out.println("relativeParent:" + (path.toFile().getParent().replaceFirst(spoolDirectory.getAbsolutePath(), "")));
    }
    
    //@Test
    public void testAllIncomeCSVsForDataErrors() throws IOException{
        String rootDirectory = "/home/turnguard/projects/swc/bde/workspace_bde/pilot-sc6-cycle1/eu-bde-sc6-parser/Input CSV/Incomes";
        
        Path path = FileSystems.getDefault().getPath(rootDirectory);
        
        BudgetDataParser parser = new CSVIncomesParser();
        
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
