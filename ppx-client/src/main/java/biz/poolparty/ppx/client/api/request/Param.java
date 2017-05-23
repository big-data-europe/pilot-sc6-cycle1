package biz.poolparty.ppx.client.api.request;

import java.io.UnsupportedEncodingException;
import static java.net.URLEncoder.encode;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.NameValuePair;



/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class Param<T> {

    private boolean required = false;    
    private final String paramName;
    private final T defaultValue;
    private T value = null;
    private boolean mustEncode = false;

    public void setMustEncode(boolean mustEncode) {
        this.mustEncode = mustEncode;
    }
    
    

    public Param(String paramName) {
        this.paramName = paramName;
        this.defaultValue = null;
    }
    
    public Param(String paramName, boolean required) {
        this.paramName = paramName;
        this.defaultValue = null;
        this.required = required;
    }
    public Param(String paramName, boolean required,boolean Encode) {
        this.paramName = paramName;
        this.defaultValue = null;
        this.required = required;
        this.mustEncode = Encode;
    }
    
    public Param(String paramName, T defaultValue, boolean required) {
        this.paramName = paramName;
        this.defaultValue = defaultValue;
        this.required = required;
    }
    public Param(String paramName, T defaultValue, boolean required,boolean Encode) {
        this.paramName = paramName;
        this.defaultValue = defaultValue;
        this.required = required;
         this.mustEncode = Encode;
    }
    
    public Param(String paramName, T defaultValue) {
        this.paramName = paramName;
        this.defaultValue = defaultValue;
        this.required = true;
    }

    public String getParamName() {
        return this.paramName;
    }

    public boolean isRequired(){
        return this.required;
    }
    
    public T getDefaultValue() {
        return this.defaultValue;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValueOrDefault() throws RequiredParameterMissingException {
        if(this.required && this.value==null && this.defaultValue==null){
            throw new RequiredParameterMissingException(this.paramName + " is missing");
        }
        return this.value != null ? this.value : this.defaultValue;
    }

    public boolean isDefaultValue() {
        return this.value == null;
    }

    public NameValuePair toNameValuePair() throws RequiredParameterMissingException { 
        NameValuePair returnNameValuePair;        
        if (this.mustEncode)
        {
            
            try {
                returnNameValuePair = new NameValuePair(paramName, encode(this.getValueOrDefault().toString(),"UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Param.class.getName()).log(Level.SEVERE, null, ex);
                returnNameValuePair = new NameValuePair(paramName, encode(this.getValueOrDefault().toString()));
            }
        }
        else
        {
            returnNameValuePair = new NameValuePair(paramName, this.getValueOrDefault().toString());
        }
        return returnNameValuePair;
    }
    
}
