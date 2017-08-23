package eu.bde.sc6.financial.ratios.api;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class UnknownCalculatorException extends Exception {

    public UnknownCalculatorException() {
    }

    public UnknownCalculatorException(String message) {
        super(message);
    }

    public UnknownCalculatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCalculatorException(Throwable cause) {
        super(cause);
    }

    public UnknownCalculatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
