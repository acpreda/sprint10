package com.acpreda.sprint10.jpa.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SearchCriteriaTest {

    @Test
    void should_create_without_exceptions() {
        SearchCriteria noArgs = new SearchCriteria();
        assertNull(noArgs.getKey());
        assertNull(noArgs.getOperation());
        assertNull(noArgs.getValue());

        SearchCriteria withArgs = new SearchCriteria("KEY", "OP", "VA");
        assertEquals("KEY", withArgs.getKey());
        assertEquals("OP", withArgs.getOperation());
        assertEquals("VA", withArgs.getValue());
    }

    @Test
    void should_set_values() {
        SearchCriteria withArgs = new SearchCriteria("KEY", "OP", "VA");
        withArgs.setKey("K2");
        withArgs.setOperation("O2");
        withArgs.setValue("V2");
        assertEquals("K2", withArgs.getKey());
        assertEquals("O2", withArgs.getOperation());
        assertEquals("V2", withArgs.getValue());

        SearchCriteria noArgs = new SearchCriteria();
        noArgs.setKey("K2");
        noArgs.setOperation("O2");
        noArgs.setValue("V2");
        assertEquals("K2", noArgs.getKey());
        assertEquals("O2", noArgs.getOperation());
        assertEquals("V2", noArgs.getValue());
    }

}