package com.acpreda.ret;

import java.util.ArrayList;

/**
 * Contenedor de mensajes de retorno
 *
 * @author acpreda
 */
public class ReturnMessages extends ArrayList<ReturnMessage> {

    private static ReturnMessages with(String level, String subject, String message) {
        ReturnMessages container = new ReturnMessages();
        container.add(new ReturnMessage(level, subject, message));
        return container;
    }

    public static ReturnMessages withError(String subject, String message) {
        return with(ReturnMessage.ERROR, subject, message);
    }

    public static ReturnMessages withInfo(String subject, String message) {
        return with(ReturnMessage.INFO, subject, message);
    }

    public static ReturnMessages withWarn(String subject, String message) {
        return with(ReturnMessage.WARN, subject, message);
    }

    public ReturnMessages msg(String level, String subject, String message) {
        this.add(new ReturnMessage(level, subject, message));
        return this;
    }

    public ReturnMessages error(String subject, String message) {
        return msg(ReturnMessage.ERROR, subject, message);
    }

    public ReturnMessages timeOut(String subject, String message) {
        return msg(ReturnMessage.TIMEOUT, subject, message);
    }

    public ReturnMessages info(String subject, String message) {
        return msg(ReturnMessage.INFO, subject, message);
    }

    public ReturnMessages warn(String subject, String message) {
        return msg(ReturnMessage.WARN, subject, message);
    }


    public boolean hasErrors() {
        for (ReturnMessage x : this) {
            if (ReturnMessage.ERROR.equals(x.getLevel()))
                return true;
        }
        return false;
    }

    public String getMessage(String level, String subject) {
        for (ReturnMessage msg : this) {
            if (msg.getLevel().equals(level)
                    && msg.getSubject() != null
                    && msg.getSubject().equals(subject)) {
                return msg.getMessage();
            }
        }
        return null;
    }

    public String getError(String subject) {
        return getMessage(ReturnMessage.ERROR, subject);
    }

    public String getWarn(String subject) {
        return getMessage(ReturnMessage.WARN, subject);
    }

    public String getInfo(String subject) {
        return getMessage(ReturnMessage.INFO, subject);
    }

}
