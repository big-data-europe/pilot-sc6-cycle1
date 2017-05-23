package biz.poolparty.ppx.client.api.request;

import org.apache.commons.httpclient.NameValuePair;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface PPXRequest {

    public NameValuePair[] getNameValuePairs() throws RequiredParameterMissingException;
}
