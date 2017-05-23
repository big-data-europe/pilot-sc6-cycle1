package biz.poolparty.ppx.client.base.request;

import biz.poolparty.ppx.client.api.request.RequiredParameterMissingException;
import biz.poolparty.ppx.client.api.request.Param;
import biz.poolparty.ppx.client.api.request.PPXRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.NameValuePair;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class PPXRequestBase implements PPXRequest {
    
    @Override
    public NameValuePair[] getNameValuePairs() throws RequiredParameterMissingException {
        Class c = this.getClass();
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        
        for(Field field : c.getDeclaredFields()){
            //We are interested in fields of type Param
            if(field.getType().equals(Param.class)){
                try {
                    //System.out.println("found single of type "+field.getType().getTypeName());
                    field.setAccessible(true);
                    Param param = (Param)field.get(this);                    
                    if(param.isRequired() || !param.isDefaultValue()){                        
                        NameValuePair thisNVP = param.toNameValuePair();                             
                        //System.out.println(param.getParamName() + " " + thisNVP.getValue() + " " + param.isDefaultValue());
                        nameValuePairs.add(thisNVP);
                    }                    
                } catch ( IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(PPXRequestBase.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            //System.out.println("\n");
            //We are also interested in fields of type List, whose generic type is Param
            if (field.getType().equals(LinkedList.class)){   
                
                ParameterizedType thisListType = (ParameterizedType) field.getGenericType();
                ParameterizedType thisListArgumentTYpe = (ParameterizedType) thisListType.getActualTypeArguments()[0];
                Type listElementRawType = thisListArgumentTYpe.getRawType();
                                
                //System.out.println("\tfound list: "+field.getName()+" of type: "+listElementRawType.getTypeName());
                
                if (listElementRawType.equals(Param.class)){
                try {
                    field.setAccessible(true);                    
                    LinkedList <Param>listOfParams = (LinkedList)field.get(this);
                    for (Param param : listOfParams)
                    {
                    if(param.isRequired() || !param.isDefaultValue()){
                        
                        NameValuePair thisNVP = param.toNameValuePair();                                 
                        //System.out.println("L:"+ param.getParamName() + " " + thisNVP.getValue() + " " + param.isDefaultValue());
                        nameValuePairs.add(thisNVP);
                        }                    
                    }
                }catch ( IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(PPXRequestBase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                }
            }
            
        }        
        return nameValuePairs.toArray(new NameValuePair[nameValuePairs.size()]);
    }
    
}
