package biz.poolparty.ppx.client.api.response;

import java.util.List;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface Concept {
    public String getId();
    public String getProject();
    public double getScore();
    public String getUri();
    public String getLanguage();
    public String getPrefLabel();
    public List<ConceptScheme> getConceptSchemes();
}
