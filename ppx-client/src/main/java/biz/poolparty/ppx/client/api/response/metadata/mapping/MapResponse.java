package biz.poolparty.ppx.client.api.response.metadata.mapping;

import biz.poolparty.ppx.client.api.response.FreeTerm;
import biz.poolparty.ppx.client.api.response.Location;
import biz.poolparty.ppx.client.api.response.PPXResponse;
import biz.poolparty.ppx.client.api.response.extract.ExtractConcept;
import java.util.List;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface MapResponse extends PPXResponse {
    public List<String> getStatements();
}
