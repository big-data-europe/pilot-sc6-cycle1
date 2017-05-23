package biz.poolparty.ppx.client.impl.response.suggest;

import biz.poolparty.ppx.client.api.response.suggest.SuggestConcept;
import biz.poolparty.ppx.client.base.response.ConceptBase;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class SuggestConceptImpl extends ConceptBase implements SuggestConcept {
    private String matchingLabel;
    
    public SuggestConceptImpl(){
        super();
    }

    @Override
    public String getMatchingLabel() {
        return matchingLabel;
    }

    public void setMatchingLabel(String matchingLabel) {
        this.matchingLabel = matchingLabel;
    }
    
    
}
