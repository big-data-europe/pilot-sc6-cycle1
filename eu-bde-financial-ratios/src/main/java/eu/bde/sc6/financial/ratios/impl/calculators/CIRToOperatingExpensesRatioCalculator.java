package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.DefaultIncomePercentageFinancialRatioCalculator;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class CIRToOperatingExpensesRatioCalculator extends DefaultIncomePercentageFinancialRatioCalculator {

    private final URI TOTAL_RATIO = new URIImpl("https://bde.poolparty.biz/SC6-Financial-Ratios/24065dbac7e5396e518b19bd23b2c64b");
    
    public CIRToOperatingExpensesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    protected URI getTotalFinancialRatio() {
        return TOTAL_RATIO;
    }

}
