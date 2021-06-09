package com.acpreda.sprint10.jpa.search;

/**
 * Excepción de uso exclusivo en caso que la expresión de criterios de búsqueda no sea correcta en cuanto a sintaxis o
 * que las propiedades no existan en la entidad objetivo.
 *
 * @author acpreda
 * @since 1.0
 */
public class InvalidPredicateException extends Exception {
    public InvalidPredicateException() {
    }

    public InvalidPredicateException(String message) {
        super(message);
    }

    public InvalidPredicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPredicateException(Throwable cause) {
        super(cause);
    }
}
