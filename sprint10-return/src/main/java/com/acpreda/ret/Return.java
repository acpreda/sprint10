package com.acpreda.ret;

import java.util.Date;

/**
 * Estructura enriquecida para retorno de funciones
 *
 * @author acpreda
 */
public class Return<V> {

    private final Date start;
    private Date end;
    private V value;

    private final ReturnMessages messages;

    public Return() {
        this.start = new Date();
        messages = new ReturnMessages();
    }

    public Return(V value) {
        this();
        this.value = value;
    }

    public static <T> Return<T> of(T t) {
        return new Return<>(t);
    }

    public Return<V> info(String subject, String message) {
        this.messages.info(subject, message);
        return this;
    }

    public Return<V> warn(String subject, String message) {
        this.messages.warn(subject, message);
        return this;
    }

    public Return<V> error(String subject, String message) {
        this.messages.error(subject, message);
        return this;
    }

    public String getError(String subject) {
        return this.messages.getError(subject);
    }

    public String getWarn(String subject) {
        return this.messages.getWarn(subject);
    }

    public String getInfo(String subject) {
        return this.messages.getInfo(subject);
    }

    public Return<V> end() {
        this.end = new Date();
        return this;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public V getValue() {
        return value;
    }

    public Return<V> setValue(V value) {
        this.value = value;
        return this;
    }

    public ReturnMessages getMessages() {
        return messages;
    }

    public boolean hasErrors() {
        return this.messages.hasErrors();
    }

    public Return<V> merge(Return<?> anotherReturn) {
        this.messages.addAll(anotherReturn.messages);
        return this;
    }

    @Override
    public String toString() {
        return "Return{" +
                "start=" + start +
                ", end=" + end +
                ", value=" + value +
                ", messages=" + messages +
                '}';
    }
}
