package eu.bde.sc6.budget.parser.api;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface BudgetDataParserRegistry {
    public BudgetDataParser getBudgetDataParser(String identifier) throws UnknownBudgetDataParserException;
}
