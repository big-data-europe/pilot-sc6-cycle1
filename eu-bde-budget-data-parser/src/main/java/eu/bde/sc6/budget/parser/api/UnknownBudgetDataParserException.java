package eu.bde.sc6.budget.parser.api;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class UnknownBudgetDataParserException extends Exception {

    public UnknownBudgetDataParserException() {
    }

    public UnknownBudgetDataParserException(String message) {
        super(message);
    }

    public UnknownBudgetDataParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownBudgetDataParserException(Throwable cause) {
        super(cause);
    }

    public UnknownBudgetDataParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
