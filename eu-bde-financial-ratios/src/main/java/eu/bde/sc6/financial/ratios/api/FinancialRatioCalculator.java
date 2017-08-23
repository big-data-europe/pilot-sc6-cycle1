package eu.bde.sc6.financial.ratios.api;

import java.util.List;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface FinancialRatioCalculator {    
    public List<Statement> calculate(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws CalculationException;    
}
