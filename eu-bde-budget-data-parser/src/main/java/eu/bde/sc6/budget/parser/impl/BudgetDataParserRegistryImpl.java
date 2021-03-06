package eu.bde.sc6.budget.parser.impl;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.BudgetDataParserRegistry;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.slf4j.LoggerFactory;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class BudgetDataParserRegistryImpl implements BudgetDataParserRegistry {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BudgetDataParserRegistryImpl.class);
    
    private static BudgetDataParserRegistryImpl registry;
    private final ServiceLoader<BudgetDataParser> loader;

    private BudgetDataParserRegistryImpl() {
        loader = ServiceLoader.load(BudgetDataParser.class);
        loader.forEach(parser -> LOG.warn("Available Parser: "+parser.getClass().getName() + " " + parser.getIdentifier()));        
    }

    public static synchronized BudgetDataParserRegistryImpl getInstance() {
        if (registry == null) {
            registry = new BudgetDataParserRegistryImpl();
        }
        return registry;
    }

    @Override
    public BudgetDataParser getBudgetDataParser(String identifier) throws UnknownBudgetDataParserException {
        for (BudgetDataParser parser : this.loader) {
            if(parser.getIdentifier().equals(identifier)){
                return parser;
            }
        }
        throw new UnknownBudgetDataParserException();
    }

    @Override
    public BudgetDataParser getBudgetDataParserForFileName(String fileName) throws UnknownBudgetDataParserException {
        for (BudgetDataParser parser : this.loader) {
            if(parser.canHandleByFileName(fileName)){
                return parser;
            }
        }
        throw new UnknownBudgetDataParserException();
    }
}
