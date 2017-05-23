package biz.poolparty.ppx.client.base.response;

import biz.poolparty.ppx.client.api.response.Location;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class LocationBase implements Location {
    private String uri;
    private String name;
    private double score;
    private String countryCode;
    private float latitude;
    private float longitude;
    private String matchedLabel;

    public LocationBase(){}
    
    @Override
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String getMatchedLabel() {
        return matchedLabel;
    }

    public void setMatchedLabel(String matchedLabel) {
        this.matchedLabel = matchedLabel;
    }
    
}
