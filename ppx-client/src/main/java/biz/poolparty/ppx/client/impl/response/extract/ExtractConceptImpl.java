package biz.poolparty.ppx.client.impl.response.extract;

import biz.poolparty.ppx.client.api.response.extract.ExtractConcept;
import biz.poolparty.ppx.client.base.response.ConceptBase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class ExtractConceptImpl extends ConceptBase implements ExtractConcept {

    private int frequencyInDocument;
    private int frequencyInDocuments;
    private List<String> altLabels = new ArrayList<>();
    private List<String> hiddenLabels = new ArrayList<>();
    private List<String> relatedConcepts = new ArrayList<>();
    private List<String> broaderConcepts = new ArrayList<>();
    private List<String> transitiveBroaderConcepts = new ArrayList<>();
    private List<String> transitiveBroaderTopConcepts = new ArrayList<>();
    private List<String> wordForms = new ArrayList<>();
    
    public ExtractConceptImpl(){}
    
    @Override
    public int getFrequencyInDocument() {
        return this.frequencyInDocument;
    }

    @Override
    public int getFrequencyInDocuments() {
        return this.frequencyInDocuments;
    }

    @Override
    public List<String> getAltLabels() {
        return this.altLabels;
    }

    @Override
    public List<String> getHiddenLabels() {
        return this.hiddenLabels;
    }

    @Override
    public List<String> getRelatedConcepts() {
        return this.relatedConcepts;
    }

    @Override
    public List<String> getBroaderConcepts() {
        return this.broaderConcepts;
    }

    @Override
    public List<String> getTransitiveBroaderConcepts() {
        return this.transitiveBroaderConcepts;
    }

    @Override
    public List<String> getTransitiveBroaderTopConcepts() {
        return this.transitiveBroaderTopConcepts;
    }

    @Override
    public List<String> getWordForms() {
        return this.wordForms;
    }

    public void setFrequencyInDocument(int frequencyInDocument) {
        this.frequencyInDocument = frequencyInDocument;
    }

    public void setFrequencyInDocuments(int frequencyInDocuments) {
        this.frequencyInDocuments = frequencyInDocuments;
    }

    public void setAltLabels(List<String> altLabels) {
        this.altLabels = altLabels;
    }

    public void setHiddenLabels(List<String> hiddenLabels) {
        this.hiddenLabels = hiddenLabels;
    }

    public void setRelatedConcepts(List<String> relatedConcepts) {
        this.relatedConcepts = relatedConcepts;
    }

    public void setBroaderConcepts(List<String> broaderConcepts) {
        this.broaderConcepts = broaderConcepts;
    }

    public void setTransitiveBroaderConcepts(List<String> transitiveBroaderConcepts) {
        this.transitiveBroaderConcepts = transitiveBroaderConcepts;
    }

    public void setTransitiveBroaderTopConcepts(List<String> transitiveBroaderTopConcepts) {
        this.transitiveBroaderTopConcepts = transitiveBroaderTopConcepts;
    }

    public void setWordForms(List<String> wordForms) {
        this.wordForms = wordForms;
    }

}
