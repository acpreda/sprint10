package com.acpreda.sprint10.jpa.search;

public class AnotherSampleTestClass {

    @Search(strategy = SearchStrategy.STRING)
    private String stringProp;

    public String getStringProp() {
        return stringProp;
    }

    public void setStringProp(String stringProp) {
        this.stringProp = stringProp;
    }
}
