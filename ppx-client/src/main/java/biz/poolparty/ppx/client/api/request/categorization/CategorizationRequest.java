package biz.poolparty.ppx.client.api.request.categorization;

import biz.poolparty.ppx.client.api.request.PPXRequest;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface CategorizationRequest extends PPXRequest {
    public void setTitle(String title);
    public void setScoringAlgorithm(String scoringAlgorithm);
}
