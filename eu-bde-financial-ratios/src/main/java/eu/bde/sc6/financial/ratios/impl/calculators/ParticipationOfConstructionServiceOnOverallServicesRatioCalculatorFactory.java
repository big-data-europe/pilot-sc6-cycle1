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
// ParticipationOfConstructionServiceOnOverallServicesRatio
public class ParticipationOfConstructionServiceOnOverallServicesRatioCalculatorFactory extends FinancialRatioCalculatorFactoryBase {
    private final URI FINANCIAL_RATIO = new URIImpl("https://bde.poolparty.biz/SC6-Financial-Ratios/ed8e58ce9c05ba31b7496e67d85d8048");
    
    private FinancialRatioCalculator instance;
    
    @Override
    public FinancialRatioCalculator get(SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        if(instance==null){
            instance = new ParticipationOfConstructionServiceOnOverallServicesRatioCalculator(FINANCIAL_RATIO, dataRepository, pptRepository);
        }
        return instance;
    }

    @Override
    public URI getFinancialRatio() {
        return FINANCIAL_RATIO;
    }
}
