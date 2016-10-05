package eu.bde.sc6.budget.parser.impl;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.BudgetDataParserRegistry;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class BudgetDataParserRegistryImpl implements BudgetDataParserRegistry {
    
    private static BudgetDataParserRegistryImpl registry;
    private final ServiceLoader<BudgetDataParser> loader;

    private BudgetDataParserRegistryImpl() {
        loader = ServiceLoader.load(BudgetDataParser.class);
    }

    public static synchronized BudgetDataParserRegistryImpl getInstance() {
        if (registry == null) {
            registry = new BudgetDataParserRegistryImpl();
        }
        return registry;
    }

    public BudgetDataParser getBudgetDataParser(String identifier) throws UnknownBudgetDataParserException {
        for( Iterator<BudgetDataParser> iter = this.loader.iterator(); iter.hasNext(); ){
            BudgetDataParser parser = iter.next();
            if(parser.getIdentifier().equals(identifier)){
                return parser;
            }
        }
        throw new UnknownBudgetDataParserException();
    }
}