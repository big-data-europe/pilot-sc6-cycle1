package biz.poolparty.ppx.client.base.response;

import biz.poolparty.ppx.client.api.response.ConceptScheme;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class ConceptSchemeBase implements ConceptScheme {

    private String uri;
    private String title;

    public ConceptSchemeBase() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
