package biz.poolparty.ppx.client.impl.response.extract;

import biz.poolparty.ppx.client.api.response.Concept;
import biz.poolparty.ppx.client.api.response.FreeTerm;
import biz.poolparty.ppx.client.api.response.Location;
import biz.poolparty.ppx.client.api.response.extract.ExtractConcept;
import biz.poolparty.ppx.client.api.response.extract.ExtractResponse;
import biz.poolparty.ppx.client.base.response.FreeTermBase;
import biz.poolparty.ppx.client.base.response.LocationBase;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class ExtractResponseImpl implements ExtractResponse {

    @JsonDeserialize(contentAs=FreeTermBase.class)    
    private List<FreeTerm> freeTerms;
    
    @JsonDeserialize(contentAs=ExtractConceptImpl.class)
    private List<ExtractConcept> concepts;
    
    @JsonDeserialize(contentAs=LocationBase.class)
    private List<Location> locations;
        
    public ExtractResponseImpl(){}
    
    @Override
    public List<FreeTerm> getFreeTerms() {
        return freeTerms;
    }

    public void setFreeTerms(List<FreeTerm> freeTerms) {
        this.freeTerms = freeTerms;
    }

    @Override
    public List<ExtractConcept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<ExtractConcept> concepts) {
        this.concepts = concepts;
    }

    @Override
    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> locations){
        this.locations = locations;
    }
    
}
