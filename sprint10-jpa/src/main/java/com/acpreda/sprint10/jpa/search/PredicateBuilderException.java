package com.acpreda.sprint10.jpa.search;

public class PredicateBuilderException extends RuntimeException {
    public PredicateBuilderException(String msg) {
        super(msg);
    }

    public PredicateBuilderException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
