package biz.poolparty.ppx.client.api.response.suggest;

import biz.poolparty.ppx.client.api.response.Concept;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface SuggestConcept extends Concept {
    public String getMatchingLabel();
}
