package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.DefaultExpensePercentageFinancialRatioCalculator;
import eu.bde.sc6.financial.ratios.base.TotalExpenseFinancialRatioCalculator;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class PreviousFinancialYearExpensesToTotalExpensesRatioCalculator extends DefaultExpensePercentageFinancialRatioCalculator {

    public PreviousFinancialYearExpensesToTotalExpensesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

}
