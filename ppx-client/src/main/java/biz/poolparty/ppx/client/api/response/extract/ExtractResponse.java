package biz.poolparty.ppx.client.api.response.extract;

import biz.poolparty.ppx.client.api.response.FreeTerm;
import biz.poolparty.ppx.client.api.response.Location;
import biz.poolparty.ppx.client.api.response.PPXResponse;
import java.util.List;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface ExtractResponse extends PPXResponse {
    public List<FreeTerm> getFreeTerms();
    public List<ExtractConcept> getConcepts();
    public List<Location> getLocations();
}
