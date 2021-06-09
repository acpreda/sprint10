package com.acpreda.ret;

import java.util.Date;

/**
 * Estructura de mensaje para los retornos complejos de funciones.
 *
 * @author acpreda
 */
public class ReturnMessage {

    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
    public static final String TIMEOUT = "TIMEOUT";

    private Date date;
    private String level;
    private String subject;
    private String message;

    public ReturnMessage() {
        this.date = new Date();
    }

    public ReturnMessage(String level, String message) {
        this(level, "", message);
    }

    public ReturnMessage(String level, String subject, String message) {
        this();
        this.level = level;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReturnMessage{" +
                "date=" + date +
                ", level='" + level + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getLevel() {
        return level;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
