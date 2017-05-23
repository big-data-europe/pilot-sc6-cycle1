package biz.poolparty.ppx.client.impl.request.annotate;

import biz.poolparty.ppx.client.base.request.PPXRequestBase;
import biz.poolparty.ppx.client.api.request.Param;
import biz.poolparty.ppx.client.api.request.annotate.AnnotateRequest;
/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class AnnotateRequestImpl extends PPXRequestBase implements AnnotateRequest {
    
    /* required */
    private final Param<String> text = new Param<>("text", true);
    private final Param<String> documentUri = new Param<>("documentUri",true);
    private final Param<String> language = new Param<>("language", "en");
    private final Param<String> projectId = new Param<>("projectId", true);
    
    /* optional */
    private final Param<String> title = new Param<>("title");
    
    private final Param<Boolean> useTransitiveBroaderConcepts = new Param<>("useTransitiveBroaderConcepts");
    private final Param<Boolean> useTransitiveBroaderTopConcepts = new Param<>("useTransitiveBroaderTopConcepts");
    private final Param<Boolean> useRelatedConcepts = new Param<>("useRelatedConcepts");
    private final Param<Integer> numberOfConcepts = new Param<>("numberOfConcepts");
    private final Param<Integer> numberOfTerms = new Param<>("numberOfTerms");
    private final Param<Boolean> filterNestedConcepts = new Param<>("filterNestedConcepts");
    private final Param<Boolean> tfidfScoring = new Param<>("tfidfScoring");
    
    private final Param<String> corpusScoring = new Param<>("corpusScoring");
    private final Param<Boolean> disambiguate = new Param<>("disambiguate");
    private final Param<Boolean> useTypes = new Param<>("useTypes");
        
    public AnnotateRequestImpl(String text, String language, String projectId, String documentUri){        
        this.text.setValue(text);
        this.language.setValue(language);
        this.projectId.setValue(projectId);
        this.documentUri.setValue(documentUri);
    }
    
    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public void setDocumentUri(String documentUri) {
        this.documentUri.setValue(documentUri);
    }

    public void setUseTransitiveBroaderConcepts(boolean useTransitiveBroaderConcepts) {
        this.useTransitiveBroaderConcepts.setValue(Boolean.valueOf(useTransitiveBroaderConcepts));
    }

    public void setUseTransitiveBroaderTopConcepts(boolean useTransitiveBroaderTopConcepts) {
        this.useTransitiveBroaderTopConcepts.setValue(Boolean.valueOf(useTransitiveBroaderTopConcepts));
    }

    public void setUseRelatedConcepts(boolean useRelatedConcepts) {
        this.useRelatedConcepts.setValue(useRelatedConcepts);
    }

    public void setNumberOfConcepts(int numberOfConcepts) {
        this.numberOfConcepts.setValue(numberOfConcepts);
    }

    public void setNumberOfTerms(int numberOfTerms) {
        this.numberOfTerms.setValue(numberOfTerms);
    }

    public void setFilterNestedConcepts(boolean filterNestedConcepts) {
        this.filterNestedConcepts.setValue(filterNestedConcepts);
    }

    public void setTfidfScoring(boolean tfidfScoring) {
        this.tfidfScoring.setValue(tfidfScoring);
    }

    public void setCorpusScoring(String corpusScoring) {
        this.corpusScoring.setValue(corpusScoring);
    }

    public void setDisambiguate(boolean disambiguate) {
        this.disambiguate.setValue(disambiguate);
    }

    public void setUseTypes(boolean useTypes) {
        this.useTypes.setValue(useTypes);
    }
        
}
