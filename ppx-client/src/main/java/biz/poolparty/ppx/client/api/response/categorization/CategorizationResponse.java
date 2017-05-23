package biz.poolparty.ppx.client.api.response.categorization;

import biz.poolparty.ppx.client.api.response.PPXResponse;
import biz.poolparty.ppx.client.impl.response.categorization.CategorizationResponseImpl;
import java.util.List;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface CategorizationResponse extends PPXResponse {
    public String getTitle();
    public String getText();
    public List<CategorizationResponseImpl.CategoryResult> getCategories();
}
