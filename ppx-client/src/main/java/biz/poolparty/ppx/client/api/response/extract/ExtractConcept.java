package biz.poolparty.ppx.client.api.response.extract;

import biz.poolparty.ppx.client.api.response.Concept;
import java.util.List;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface ExtractConcept extends Concept {
    public int getFrequencyInDocument();
    public int getFrequencyInDocuments();
    public List<String> getAltLabels();
    public List<String> getHiddenLabels();
    public List<String> getRelatedConcepts();
    public List<String> getBroaderConcepts();
    public List<String> getTransitiveBroaderConcepts();
    public List<String> getTransitiveBroaderTopConcepts();
    public List<String> getWordForms();
}
