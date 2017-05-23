package biz.poolparty.ppx.client.impl.response.suggest;

import biz.poolparty.ppx.client.api.response.suggest.SuggestConcept;
import biz.poolparty.ppx.client.api.response.suggest.SuggestResponse;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class SuggestResponseImpl implements SuggestResponse {
    private boolean success;
    private String message;
    private Long total;
    
    @JsonDeserialize(contentAs=SuggestConceptImpl.class)
    private List<SuggestConcept> suggestedConcepts;

    public SuggestResponseImpl(){}
    
    @Override
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public List<SuggestConcept> getSuggestedConcepts() {
        return suggestedConcepts;
    }

    public void setSuggestedConcepts(List<SuggestConcept> suggestedConcepts) {
        this.suggestedConcepts = suggestedConcepts;
    }

}
