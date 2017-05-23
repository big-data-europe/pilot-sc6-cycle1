package biz.poolparty.ppx.client.api.request;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface ExtractRequest extends PPXRequest {
    public void setTitle(String title);
    public void setDocumentId(String documentId);
    public void setUseTransitiveBroaderConcepts(boolean useTransitiveBroaderConcepts);
    public void setUseTransitiveBroaderTopConcepts(boolean useTransitiveBroaderTopConcepts);
    public void setUseRelatedConcepts(boolean useRelatedConcepts);
    public void setNumberOfConcepts(int numberOfConcepts);
    public void setNumberOfTerms(int numberOfTerms);
    public void setFilterNestedConcepts(boolean filterNestedConcepts);
    public void setTfidfScoring(boolean tfidfScoring);
    public void setCorpusScoring(String corpusScoring);
    public void setEnrichmentSource(String enrichmentSource);
    public void setLocationExtraction(boolean locationExtraction);
    public void setFindPersonNames(boolean findPersonNames);
    public void setRegexFilename(String regexFilename);
    public void setCategorize(boolean categorize);
    public void setCategorizationWithPpxBoost(boolean categorizationWithPpxBoost);
    public void setDisambiguate(boolean disambiguate);
    public void setUseTypes(boolean useTypes);
    public void setLemmatization(boolean lemmatization);
    public void setSentimentAnalysis(boolean sentimentAnalysis);
}
