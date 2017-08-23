package eu.bde.sc6.financial.ratios.base;

import eu.bde.sc6.financial.ratios.api.CalculationException;
import eu.bde.sc6.financial.ratios.api.FinancialRatioCalculator;
import java.util.List;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class IncomePercentageFinancialCalculatorWrapper extends FinancialRatioCalculatorBase {
    private DefaultIncomeFinancialCalculatorBase nominator;
    private DefaultIncomeFinancialCalculatorBase deNominator;

    public IncomePercentageFinancialCalculatorWrapper(
            URI financialRatio, 
            SPARQLRepository dataRepository, 
            SPARQLRepository pptRepository,
            DefaultIncomeFinancialCalculatorBase nominator, 
            DefaultIncomeFinancialCalculatorBase deNominator
    ) {
        super(financialRatio, dataRepository, pptRepository);
        this.nominator = nominator;
        this.deNominator = deNominator;                
    }
    @Override
    public List<Statement> calculate(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws CalculationException {
        this.nominator.getSum(dataGraph, buyerSeller, issued);
        return null;
    }
}
