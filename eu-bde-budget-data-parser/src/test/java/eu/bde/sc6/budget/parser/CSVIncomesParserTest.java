package eu.bde.sc6.budget.parser;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.BudgetDataParserRegistry;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import eu.bde.sc6.budget.parser.impl.BudgetDataParserRegistryImpl;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testAvailableInRegistry() throws UnknownBudgetDataParserException {
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser("thessaloniki.incomes.csv");
        assertNotNull(parser);
    }
    
    @Test
    public void testParseSimpleFile() throws UnknownBudgetDataParserException, IOException, TransformationException{
        String fileName = "2016_06_30_21.29.csv";
        byte[] file = IOUtils.toByteArray(CSVIncomesParserTest.class.getResourceAsStream("/thessaloniki/incomes/".concat(fileName)));
        BudgetDataParser parser = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParser("thessaloniki.incomes.csv");        
        parser.transform(fileName, file);
    }
}
