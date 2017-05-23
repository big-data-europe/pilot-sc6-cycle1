package biz.poolparty.ppx.client.api.response;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface Location {
    public String getUri();
    public String getName();
    public double getScore();
    public String getCountryCode();
    public float getLatitude();
    public float getLongitude();
    public String getMatchedLabel();
}
