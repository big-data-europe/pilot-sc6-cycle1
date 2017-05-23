package biz.poolparty.ppx.client.api.request.annotate;

import biz.poolparty.ppx.client.api.request.PPXRequest;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface AnnotateRequest extends PPXRequest {
    public void setTitle(String title);
    public void setDocumentUri(String documentUri);
    public void setUseTransitiveBroaderConcepts(boolean useTransitiveBroaderConcepts);
    public void setUseTransitiveBroaderTopConcepts(boolean useTransitiveBroaderTopConcepts);
    public void setUseRelatedConcepts(boolean useRelatedConcepts);
    public void setNumberOfConcepts(int numberOfConcepts);
    public void setNumberOfTerms(int numberOfTerms);
    public void setFilterNestedConcepts(boolean filterNestedConcepts);
    public void setTfidfScoring(boolean tfidfScoring);
    public void setCorpusScoring(String corpusScoring);
    public void setDisambiguate(boolean disambiguate);
    public void setUseTypes(boolean useTypes);     
}
