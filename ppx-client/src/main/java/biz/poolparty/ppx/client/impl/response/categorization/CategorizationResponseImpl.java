package biz.poolparty.ppx.client.impl.response.categorization;

import biz.poolparty.ppx.client.api.response.categorization.CategorizationResponse;
import java.util.List;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class CategorizationResponseImpl implements CategorizationResponse {
    private String title;
    private String text;
    private List<CategoryResult> categories;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CategoryResult> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResult> categories) {
        this.categories = categories;
    }
    
    public static class CategoryResult {
        private String prefLabel;
        private String uri;
        private Double score;
        private List<CategoryConceptResult> categoryConceptResults;

        public String getPrefLabel() {
            return prefLabel;
        }

        public void setPrefLabel(String prefLabel) {
            this.prefLabel = prefLabel;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public List<CategoryConceptResult> getCategoryConceptResults() {
            return categoryConceptResults;
        }

        public void setCategoryConceptResults(List<CategoryConceptResult> categoryConceptResults) {
            this.categoryConceptResults = categoryConceptResults;
        }
        
    }
    
    public static class CategoryConceptResult {
        private String prefLabel;
        private String uri;
        private Double score;

        public String getPrefLabel() {
            return prefLabel;
        }

        public void setPrefLabel(String prefLabel) {
            this.prefLabel = prefLabel;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }
        
    }
}
