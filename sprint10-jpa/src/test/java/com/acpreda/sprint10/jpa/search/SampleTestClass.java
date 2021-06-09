package com.acpreda.sprint10.jpa.search;

import com.acpreda.sprint10.commons.HasId;

import java.util.Date;
import java.util.UUID;

public class SampleTestClass implements HasId<String> {

    private String id;

    @Search(strategy = SearchStrategy.STRING)
    private String stringProp;

    @Search(strategy = SearchStrategy.STRING_NORMALIZADO)
    private String stringNormProp;

    @Search(strategy = SearchStrategy.NUMBER)
    private int intProp;

    @Search(strategy = SearchStrategy.UUID)
    private UUID uuidProp;

    @Search(strategy = SearchStrategy.BOOLEAN)
    private boolean booleanProp;

    @Search(strategy = SearchStrategy.DATE)
    private Date dateProp;

    private AnotherSampleTestClass objectProp;

    public String getStringProp() {
        return stringProp;
    }

    public void setStringProp(String stringProp) {
        this.stringProp = stringProp;
    }

    public String getStringNormProp() {
        return stringNormProp;
    }

    public void setStringNormProp(String stringNormProp) {
        this.stringNormProp = stringNormProp;
    }

    public int getIntProp() {
        return intProp;
    }

    public void setIntProp(int intProp) {
        this.intProp = intProp;
    }

    public UUID getUuidProp() {
        return uuidProp;
    }

    public void setUuidProp(UUID uuidProp) {
        this.uuidProp = uuidProp;
    }

    public boolean isBooleanProp() {
        return booleanProp;
    }

    public void setBooleanProp(boolean booleanProp) {
        this.booleanProp = booleanProp;
    }

    public Date getDateProp() {
        return dateProp;
    }

    public void setDateProp(Date dateProp) {
        this.dateProp = dateProp;
    }

    public AnotherSampleTestClass getObjectProp() {
        return objectProp;
    }

    public void setObjectProp(AnotherSampleTestClass objectProp) {
        this.objectProp = objectProp;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
