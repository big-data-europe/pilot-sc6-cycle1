package biz.poolparty.ppx.client.impl.request.categorization;

import biz.poolparty.ppx.client.api.request.Param;
import biz.poolparty.ppx.client.api.request.categorization.CategorizationRequest;
import biz.poolparty.ppx.client.base.request.PPXRequestBase;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class CategorizationRequestImpl extends PPXRequestBase implements CategorizationRequest {
    /* required */
    private final Param<String> text = new Param<>("text", true);
    private final Param<String> language = new Param<>("language", "en");
    private final Param<String> projectId = new Param<>("projectId", true);
    
    /* optional */
    private final Param<String> title = new Param<>("title");
    private final Param<String> scoringAlgorithm = new Param<>("scoringAlgorithm");
    
    public CategorizationRequestImpl(String text, String language, String projectId){
        this.text.setValue(text);
        this.language.setValue(language);
        this.projectId.setValue(projectId);
    }
    
    public void setTitle(String title) {
        this.title.setValue(title);
    }
    
    public void setScoringAlgorithm(String scoringAlgorithm) {
        this.scoringAlgorithm.setValue(scoringAlgorithm);
    }    
}
