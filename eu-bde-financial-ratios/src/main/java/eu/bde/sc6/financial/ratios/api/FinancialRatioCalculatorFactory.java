package eu.bde.sc6.financial.ratios.api;

import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface FinancialRatioCalculatorFactory {
    public URI getFinancialRatio();
    public FinancialRatioCalculator get(SPARQLRepository dataRepository, SPARQLRepository pptRepository);
}
