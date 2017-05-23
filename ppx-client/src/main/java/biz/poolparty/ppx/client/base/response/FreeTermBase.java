package biz.poolparty.ppx.client.base.response;

import biz.poolparty.ppx.client.api.response.FreeTerm;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class FreeTermBase implements FreeTerm {

    private String textValue;
    private int score;
    private int frequencyInDocument;
    private int frequencyInDocuments;

    public FreeTermBase() {
    }

    @Override
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Override
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getFrequencyInDocument() {
        return frequencyInDocument;
    }

    public void setFrequencyInDocument(int frequencyInDocument) {
        this.frequencyInDocument = frequencyInDocument;
    }

    @Override
    public int getFrequencyInDocuments() {
        return this.frequencyInDocuments;
    }

    public void setFrequencyInDocuments(int frequencyInDocuments){
        this.frequencyInDocuments = frequencyInDocuments;
    }
}
