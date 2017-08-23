package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.DefaultIncomeValueFinancialRatioCalculator;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class TotalRevenuesCalculator extends DefaultIncomeValueFinancialRatioCalculator {

    public TotalRevenuesCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

}
