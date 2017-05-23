package biz.poolparty.ppx.client.impl.response.metadata.mapping;

import biz.poolparty.ppx.client.api.response.metadata.mapping.MapResponse;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author syats
 */
public class MapResponseImpl implements MapResponse {

    private LinkedList<String> statements = new LinkedList<String>();
       
    @Override
    public List<String> getStatements() {
        return this.statements;
    }
    
    public MapResponseImpl(String response)
    {
        String[] lines = response.split("\n");
        for (String l:lines) {
            if (!l.isEmpty()){
                this.statements.add(l);
            }
        }              
    }
    
}
