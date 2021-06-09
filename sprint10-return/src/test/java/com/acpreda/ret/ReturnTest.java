package com.acpreda.ret;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReturnTest {

    @Test
    void should_construct_without_exceptions() {
        long delta = 100; //100ms diff

        Return<String> noValue = new Return<>();
        assertNull(noValue.getValue());
        assertTrue(new Date().getTime() - noValue.getStart().getTime() < delta);
        assertNotNull(noValue.getMessages());
        assertEquals(0, noValue.getMessages().size());
        assertFalse(noValue.hasErrors());

        Return<String> withValue = new Return<>("VALUE");
        assertEquals("VALUE", withValue.getValue());
        assertTrue(new Date().getTime() - withValue.getStart().getTime() < delta);
        assertNotNull(withValue.getMessages());
        assertEquals(0, withValue.getMessages().size());
        assertFalse(withValue.hasErrors());

        Return<String> ofValue = Return.of("VALUE");
        assertEquals("VALUE", ofValue.getValue());
        assertTrue(new Date().getTime() - ofValue.getStart().getTime() < delta);
        assertNotNull(ofValue.getMessages());
        assertEquals(0, ofValue.getMessages().size());
        assertFalse(ofValue.hasErrors());

    }

    @Test
    void should_return_toString() {
        Return<String> noValue = new Return<>();
        assertNotNull(noValue.toString());
    }

    @Test
    void should_set_value() {
        Return<String> noValue = new Return<>();
        noValue.setValue("VALUE");
        assertEquals("VALUE", noValue.getValue());
    }

    @Test
    void should_add_messages() {
        Return<String> noValue = new Return<>();
        noValue.info("INFO_SUBJECT", "INFO_MESSAGE");
        noValue.warn("WARN_SUBJECT", "WARN_MESSAGE");
        noValue.error("ERROR_SUBJECT", "ERROR_MESSAGE");

        ReturnMessages returnMessages = noValue.getMessages();
        assertEquals(3, returnMessages.size());

        ReturnMessage withErrorMessage = returnMessages.get(2);
        assertEquals(ReturnMessage.ERROR, withErrorMessage.getLevel());
        assertEquals("ERROR_SUBJECT", withErrorMessage.getSubject());
        assertEquals("ERROR_MESSAGE", withErrorMessage.getMessage());

        ReturnMessage withWarnMessage = returnMessages.get(1);
        assertEquals(ReturnMessage.WARN, withWarnMessage.getLevel());
        assertEquals("WARN_SUBJECT", withWarnMessage.getSubject());
        assertEquals("WARN_MESSAGE", withWarnMessage.getMessage());

        ReturnMessage withInfoMessage = returnMessages.get(0);
        assertEquals(ReturnMessage.INFO, withInfoMessage.getLevel());
        assertEquals("INFO_SUBJECT", withInfoMessage.getSubject());
        assertEquals("INFO_MESSAGE", withInfoMessage.getMessage());

        assertTrue(noValue.hasErrors());
    }

    @Test
    void should_end() {
        Return<String> noValue = new Return<>();
        assertNull(noValue.getEnd());
        noValue.end();
        assertNotNull(noValue.getEnd());
        long delta = 100;
        assertTrue(new Date().getTime() - noValue.getEnd().getTime() < delta);
    }

    @Test
    void should_merge_messages() {
        Return<String> noValue1 = new Return<>();
        noValue1.info("SUBJECT_1", "MESSAGE_1");
        assertEquals(1, noValue1.getMessages().size());
        assertFalse(noValue1.hasErrors());

        Return<String> noValue2 = new Return<>();
        noValue2.error("SUBJECT_2", "MESSAGE_2");
        assertEquals(1, noValue2.getMessages().size());
        assertTrue(noValue2.hasErrors());

        noValue1.merge(noValue2);
        assertEquals(2, noValue1.getMessages().size());
        assertTrue(noValue1.hasErrors());
    }

}