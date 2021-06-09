package com.acpreda.sprint10.jpa.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchStrategyTest {

    @Test
    void should_have_all_members() {
        assertEquals(SearchStrategy.STRING, SearchStrategy.valueOf("STRING"));
        assertEquals(SearchStrategy.STRING_NORMALIZADO, SearchStrategy.valueOf("STRING_NORMALIZADO"));
        assertEquals(SearchStrategy.NUMBER, SearchStrategy.valueOf("NUMBER"));
        assertEquals(SearchStrategy.UUID, SearchStrategy.valueOf("UUID"));
        assertEquals(SearchStrategy.BOOLEAN, SearchStrategy.valueOf("BOOLEAN"));
        assertEquals(SearchStrategy.DATE, SearchStrategy.valueOf("DATE"));
    }

}