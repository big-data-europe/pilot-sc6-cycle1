package biz.poolparty.ppx.client.impl.request.extract;

import biz.poolparty.ppx.client.api.request.ExtractRequest;
import biz.poolparty.ppx.client.base.request.PPXRequestBase;
import biz.poolparty.ppx.client.api.request.Param;
/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class ExtractRequestImpl extends PPXRequestBase implements ExtractRequest {
    
    /* required */
    private final Param<String> text = new Param<>("text", true);
    private final Param<String> language = new Param<>("language", "en");
    private final Param<String> projectId = new Param<>("projectId", true);
    
    /* optional */
    private final Param<String> title = new Param<>("title");
    private final Param<String> documentId = new Param<>("documentId");
    private final Param<Boolean> useTransitiveBroaderConcepts = new Param<>("useTransitiveBroaderConcepts");
    private final Param<Boolean> useTransitiveBroaderTopConcepts = new Param<>("useTransitiveBroaderTopConcepts");
    private final Param<Boolean> useRelatedConcepts = new Param<>("useRelatedConcepts");
    private final Param<Integer> numberOfConcepts = new Param<>("numberOfConcepts");
    private final Param<Integer> numberOfTerms = new Param<>("numberOfTerms");
    private final Param<Boolean> filterNestedConcepts = new Param<>("filterNestedConcepts");
    private final Param<Boolean> tfidfScoring = new Param<>("tfidfScoring");
    
    private final Param<String> corpusScoring = new Param<>("corpusScoring");
    private final Param<String> enrichmentSource = new Param<>("enrichmentSource");
    
    private final Param<Boolean> locationExtraction = new Param<>("locationExtraction");
    private final Param<Boolean> findPersonNames = new Param<>("findPersonNames");
   
    private final Param<String> regexFilename = new Param<>("regexFilename");
    
    private final Param<Boolean> categorize = new Param<>("categorize");
    private final Param<Boolean> categorizationWithPpxBoost = new Param<>("categorizationWithPpxBoost");
    private final Param<Boolean> disambiguate = new Param<>("disambiguate");
    private final Param<Boolean> useTypes = new Param<>("useTypes");
    private final Param<Boolean> lemmatization = new Param<>("lemmatization");
    private final Param<Boolean> sentimentAnalysis = new Param<>("sentimentAnalysis");
        
    public ExtractRequestImpl(String text, String language, String projectId){        
        this.text.setValue(text);
        this.language.setValue(language);
        this.projectId.setValue(projectId);
    }
    
    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public void setDocumentId(String documentId) {
        this.documentId.setValue(documentId);
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

    public void setEnrichmentSource(String enrichmentSource) {
        this.enrichmentSource.setValue(enrichmentSource);
    }

    public void setLocationExtraction(boolean locationExtraction) {
        this.locationExtraction.setValue(locationExtraction);
    }

    public void setFindPersonNames(boolean findPersonNames) {
        this.findPersonNames.setValue(findPersonNames);
    }

    public void setRegexFilename(String regexFilename) {
        this.regexFilename.setValue(regexFilename);
    }

    public void setCategorize(boolean categorize) {
        this.categorize.setValue(categorize);
    }

    public void setCategorizationWithPpxBoost(boolean categorizationWithPpxBoost) {
        this.categorizationWithPpxBoost.setValue(categorizationWithPpxBoost);
    }

    public void setDisambiguate(boolean disambiguate) {
        this.disambiguate.setValue(disambiguate);
    }

    public void setUseTypes(boolean useTypes) {
        this.useTypes.setValue(useTypes);
    }

    public void setLemmatization(boolean lemmatization) {
        this.lemmatization.setValue(lemmatization);
    }

    public void setSentimentAnalysis(boolean sentimentAnalysis) {
        this.sentimentAnalysis.setValue(sentimentAnalysis);
    }
        
}
