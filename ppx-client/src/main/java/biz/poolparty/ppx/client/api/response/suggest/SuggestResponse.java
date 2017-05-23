package biz.poolparty.ppx.client.api.response.suggest;

import biz.poolparty.ppx.client.api.response.PPXResponse;
import java.util.List;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface SuggestResponse extends PPXResponse {
    public boolean isSuccess();
    public String getMessage();
    public Long getTotal();
    public List<SuggestConcept> getSuggestedConcepts();
}
