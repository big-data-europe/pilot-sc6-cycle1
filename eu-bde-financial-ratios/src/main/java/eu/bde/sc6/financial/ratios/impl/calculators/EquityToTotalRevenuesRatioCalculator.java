package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.DefaultIncomePercentageFinancialRatioCalculator;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class EquityToTotalRevenuesRatioCalculator extends DefaultIncomePercentageFinancialRatioCalculator {

    public EquityToTotalRevenuesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

}
