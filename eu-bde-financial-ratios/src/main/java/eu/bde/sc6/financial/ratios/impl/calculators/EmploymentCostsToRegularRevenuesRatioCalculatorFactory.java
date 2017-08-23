package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.api.FinancialRatioCalculator;
import eu.bde.sc6.financial.ratios.base.FinancialRatioCalculatorFactoryBase;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class EmploymentCostsToRegularRevenuesRatioCalculatorFactory extends FinancialRatioCalculatorFactoryBase {
    private final URI FINANCIAL_RATIO = new URIImpl("https://bde.poolparty.biz/SC6-Financial-Ratios/ebb77d1d9bf64c6da1b512dd71b4fa6a");
    
    private FinancialRatioCalculator instance;
    
    @Override
    public FinancialRatioCalculator get(SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        if(instance==null){
            instance = new EmploymentCostsToRegularRevenuesRatioCalculator(FINANCIAL_RATIO, dataRepository, pptRepository);
        }
        return instance;
    }

    @Override
    public URI getFinancialRatio() {
        return FINANCIAL_RATIO;
    }
}
