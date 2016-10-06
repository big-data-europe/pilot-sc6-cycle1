package eu.bde.sc6.budget.parser;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.BudgetDataParserRegistry;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import eu.bde.sc6.budget.parser.impl.BudgetDataParserRegistryImpl;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
    
    @Test
    public void testCanHandleByFileName(){
        System.err.println("/var/lib/bde/flume/sc6/thessaloniki/csv/incomes/2016_06_30_21.31.csv".matches(".*(xthessaloniki/csv/incomes).*"));
    }
            
}
