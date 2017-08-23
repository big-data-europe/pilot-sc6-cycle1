package eu.bde.sc6.financial.ratios.api;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface FinancialRatioCalculatorRegistry {
    public FinancialRatioCalculator getCalculatorByClassName(String className) throws UnknownCalculatorException;
}
