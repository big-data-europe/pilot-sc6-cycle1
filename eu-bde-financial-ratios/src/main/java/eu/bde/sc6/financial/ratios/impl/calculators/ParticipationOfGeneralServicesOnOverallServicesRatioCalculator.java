package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.ArithmeticFinancialRatioCalculatorBase;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 * unregistered for now due to virtuoso error 
 * @author http://www.turnguard.com/turnguard
 */
public class ParticipationOfGeneralServicesOnOverallServicesRatioCalculator extends ArithmeticFinancialRatioCalculatorBase {

    public ParticipationOfGeneralServicesOnOverallServicesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

}
