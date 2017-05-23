package biz.poolparty.ppx.client.api.request;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class RequiredParameterMissingException extends Exception {

    public RequiredParameterMissingException() {
    }

    public RequiredParameterMissingException(String message) {
        super(message);
    }

    public RequiredParameterMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequiredParameterMissingException(Throwable cause) {
        super(cause);
    }

    public RequiredParameterMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
