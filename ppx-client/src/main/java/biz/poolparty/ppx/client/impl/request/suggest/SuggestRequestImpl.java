package biz.poolparty.ppx.client.impl.request.suggest;

import biz.poolparty.ppx.client.api.request.Param;
import biz.poolparty.ppx.client.api.request.suggest.SuggestRequest;
import biz.poolparty.ppx.client.base.request.PPXRequestBase;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class SuggestRequestImpl extends PPXRequestBase implements SuggestRequest {
    /* required */    
    private final Param<String> language = new Param<>("language", "en");
    private final Param<String> projectId = new Param<>("projectId", true);
    private final Param<String> searchString = new Param<>("searchString", true);
    /* optional */
    private final Param<Integer> offset = new Param<>("offset");
    private final Param<Integer> numberOfConcepts = new Param<>("numberOfConcepts");
    private final Param<Boolean> useBlacklisted = new Param<>("useBlacklisted");
    private final Param<Boolean> useCompoundWord = new Param<>("useCompoundWord");
    
    public SuggestRequestImpl(String searchString, String language, String projectId){
        this.searchString.setValue(searchString);
        this.projectId.setValue(projectId);
        this.language.setValue(language);
    }
    
    public void setOffset(int offset){
        this.offset.setValue(offset);
    }
    public void setNumberOfConcepts(int numberOfConcepts){
        this.numberOfConcepts.setValue(numberOfConcepts);
    }
    public void setUseBlacklisted(boolean useBlacklisted){
        this.useBlacklisted.setValue(useBlacklisted);
    }
    public void setUseCompoundWord(boolean useCompoundWord){
        this.useCompoundWord.setValue(useCompoundWord);
    }
    
}
