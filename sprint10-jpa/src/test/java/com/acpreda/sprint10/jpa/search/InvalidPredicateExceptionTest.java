package com.acpreda.sprint10.jpa.search;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InvalidPredicateExceptionTest {

    @Test
    void should_create_without_exceptions() {
        InvalidPredicateException noArgs = new InvalidPredicateException();
        assertNull(noArgs.getMessage());
        InvalidPredicateException withMessage = new InvalidPredicateException("A MESSAGE");
        assertEquals("A MESSAGE", withMessage.getMessage());
        RuntimeException causeException = new RuntimeException("CAUSE MESSAGE");
        InvalidPredicateException withCause = new InvalidPredicateException(causeException);
        assertEquals(causeException, withCause.getCause());
        assertEquals("CAUSE MESSAGE", withCause.getCause().getMessage());
        InvalidPredicateException withMessageAndCause = new InvalidPredicateException("A MESSAGE", causeException);
        assertEquals(causeException, withMessageAndCause.getCause());
        assertEquals("CAUSE MESSAGE", withMessageAndCause.getCause().getMessage());
        assertEquals("A MESSAGE", withMessageAndCause.getMessage());
    }

}