package biz.poolparty.ppx.client.base.response;

import biz.poolparty.ppx.client.api.response.Concept;
import biz.poolparty.ppx.client.api.response.ConceptScheme;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class ConceptBase implements Concept {

    private String id;
    private String project;
    private double score;
    private String uri;
    private String language;
    private String prefLabel;
    
    @JsonDeserialize(contentAs=ConceptSchemeBase.class)
    private List<ConceptScheme> conceptSchemes = new ArrayList<>();

    public ConceptBase() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String getPrefLabel() {
        return prefLabel;
    }

    public void setPrefLabel(String prefLabel) {
        this.prefLabel = prefLabel;
    }

    @Override
    public List<ConceptScheme> getConceptSchemes() {
        return conceptSchemes;
    }

    public void setConceptSchemes(List<ConceptScheme> conceptSchemes) {
        this.conceptSchemes = conceptSchemes;
    }
}
