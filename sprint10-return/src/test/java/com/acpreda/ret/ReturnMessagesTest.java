package com.acpreda.ret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReturnMessagesTest {

    @Test
    void should_construct_without_exceptions() {
        ReturnMessages returnMessages = new ReturnMessages();
        assertNotNull(returnMessages);

        ReturnMessages withError = ReturnMessages.withError("ERROR_SUBJECT", "ERROR_MESSAGE");
        assertNotNull(withError);
        assertTrue(withError.size() > 0);
        ReturnMessage withErrorMessage = withError.get(0);
        assertEquals(ReturnMessage.ERROR, withErrorMessage.getLevel());
        assertEquals("ERROR_SUBJECT", withErrorMessage.getSubject());
        assertEquals("ERROR_MESSAGE", withErrorMessage.getMessage());
        assertTrue(withError.hasErrors());

        ReturnMessages withWarn = ReturnMessages.withWarn("WARN_SUBJECT", "WARN_MESSAGE");
        assertNotNull(withWarn);
        assertTrue(withWarn.size() > 0);
        ReturnMessage withWarnMessage = withWarn.get(0);
        assertEquals(ReturnMessage.WARN, withWarnMessage.getLevel());
        assertEquals("WARN_SUBJECT", withWarnMessage.getSubject());
        assertEquals("WARN_MESSAGE", withWarnMessage.getMessage());
        assertFalse(withWarn.hasErrors());

        ReturnMessages withInfo = ReturnMessages.withInfo("INFO_SUBJECT", "INFO_MESSAGE");
        assertNotNull(withInfo);
        assertTrue(withInfo.size() > 0);
        ReturnMessage withInfoMessage = withInfo.get(0);
        assertEquals(ReturnMessage.INFO, withInfoMessage.getLevel());
        assertEquals("INFO_SUBJECT", withInfoMessage.getSubject());
        assertEquals("INFO_MESSAGE", withInfoMessage.getMessage());

    }

    @Test
    void should_add_messages() {
        ReturnMessages returnMessages = new ReturnMessages();
        returnMessages.info("INFO_SUBJECT", "INFO_MESSAGE");
        returnMessages.warn("WARN_SUBJECT", "WARN_MESSAGE");
        returnMessages.error("ERROR_SUBJECT", "ERROR_MESSAGE");
        returnMessages.timeOut("TIMEOUT_SUBJECT", "TIMEOUT_MESSAGE");

        assertEquals(4, returnMessages.size());

        ReturnMessage withTimeoutMessage = returnMessages.get(3);
        assertEquals(ReturnMessage.TIMEOUT, withTimeoutMessage.getLevel());
        assertEquals("TIMEOUT_SUBJECT", withTimeoutMessage.getSubject());
        assertEquals("TIMEOUT_MESSAGE", withTimeoutMessage.getMessage());

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

        assertTrue(returnMessages.hasErrors());
    }
}