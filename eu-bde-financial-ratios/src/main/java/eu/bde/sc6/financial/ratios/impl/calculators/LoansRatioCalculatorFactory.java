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
public class LoansRatioCalculatorFactory extends FinancialRatioCalculatorFactoryBase {
    private final URI FINANCIAL_RATIO = new URIImpl("https://bde.poolparty.biz/SC6-Financial-Ratios/eea51dcf864028eee10e3e3690b6e494");
    
    private FinancialRatioCalculator instance;
    
    @Override
    public FinancialRatioCalculator get(SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        if(instance==null){
            instance = new LoansRatioCalculator(FINANCIAL_RATIO, dataRepository, pptRepository);
        }
        return instance;
    }

    @Override
    public URI getFinancialRatio() {
        return FINANCIAL_RATIO;
    }
}
