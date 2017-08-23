package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.ArithmeticFinancialRatioCalculatorBase;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class ParticipationOfFinancialAndAdministrativeServicesOnOverallServicesRatioCalculator extends ArithmeticFinancialRatioCalculatorBase {

    public ParticipationOfFinancialAndAdministrativeServicesOnOverallServicesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

}
