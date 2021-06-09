package com.acpreda.ret;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReturnMessageTest {

    @Test
    void should_have_constants() {
        assertNotNull(ReturnMessage.INFO);
        assertNotNull(ReturnMessage.ERROR);
        assertNotNull(ReturnMessage.WARN);
        assertNotNull(ReturnMessage.TIMEOUT);
    }

    @Test
    void should_return_toString() {
        ReturnMessage returnMessage = new ReturnMessage();
        assertNotNull(returnMessage.toString());
    }

    @Test
    void should_construct_without_exceptions() {
        long delta = 100; // 100ms of maximum difference between current date and creation date
        ReturnMessage noParams = new ReturnMessage();
        assertNotNull(noParams.getDate());
        assertTrue(new Date().getTime() - noParams.getDate().getTime() < delta);
        assertNull(noParams.getLevel());
        assertNull(noParams.getSubject());
        assertNull(noParams.getMessage());

        ReturnMessage withLevelAndMessage = new ReturnMessage("LEVEL##", "MESSAGE##");
        assertNotNull(withLevelAndMessage.getDate());
        assertTrue(new Date().getTime() - withLevelAndMessage.getDate().getTime() < delta);
        assertEquals("LEVEL##", withLevelAndMessage.getLevel());
        assertEquals("", withLevelAndMessage.getSubject());
        assertEquals("MESSAGE##", withLevelAndMessage.getMessage());

        ReturnMessage withLevelAndSubjectAndMessage =
                new ReturnMessage("LEVEL##", "SUBJECT##", "MESSAGE##");
        assertNotNull(withLevelAndSubjectAndMessage.getDate());
        assertTrue(new Date().getTime() - withLevelAndSubjectAndMessage.getDate().getTime() < delta);
        assertEquals("LEVEL##", withLevelAndSubjectAndMessage.getLevel());
        assertEquals("SUBJECT##", withLevelAndSubjectAndMessage.getSubject());
        assertEquals("MESSAGE##", withLevelAndSubjectAndMessage.getMessage());

    }

}