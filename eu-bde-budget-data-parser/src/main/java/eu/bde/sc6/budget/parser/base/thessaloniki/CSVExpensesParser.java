package eu.bde.sc6.budget.parser.base.thessaloniki;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import java.util.ArrayList;
import java.util.List;
import org.openrdf.model.Statement;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class CSVExpensesParser implements BudgetDataParser {
    
    private final static String IDENTIFIER = "thessaloniki.expenses.csv";
    
    @Override
    public List<Statement> transform(String fileName, byte[] file) {
        return new ArrayList<>();
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

}
