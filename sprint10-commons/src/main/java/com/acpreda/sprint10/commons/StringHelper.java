package com.acpreda.sprint10.commons;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Métodos de apoyo para operaciones con cadenas de texto
 *
 * @author acpreda
 * @since 1.0
 */
public class StringHelper {

    /**
     * Expresión del complemento del lenguaje normalizado
     */
    private static final Pattern NO_ALFABETO_NORMALIZADO_PATTERN = Pattern.compile("[^0-9A-Z ]+");

    /**
     * Expresión del lenguaje regular de las cadenas de texto compuestas por más de un espacio
     */
    private static final Pattern MAS_DE_UN_ESPACIO_PATTERN = Pattern.compile("[ ][ ]+");

    private StringHelper() {
    }

    /**
     * Función que normaliza una cadena de texto
     *
     * @param str La cadena de texto a normalizar
     * @return Una nueva cadena de texto en lenguaje normalizado
     */
    public static String normalizar(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        str = str.toUpperCase();
        str = StringUtils.replaceChars(str, "ÁÉÍÓÚÜÑ", "AEIOUUN");
        str = RegExUtils.replaceAll(str, NO_ALFABETO_NORMALIZADO_PATTERN, " ");
        str = RegExUtils.replaceAll(str, MAS_DE_UN_ESPACIO_PATTERN, " ");
        str = str.trim();
        return str;
    }

    /**
     * Convierte una cadena de texto a notación snake case
     *
     * @param str Cadena a convertir
     * @return Una cadena en notación snake case
     */
    public static String toSnakeCase(String str) {
        if (StringUtils.isBlank(str))
            return str;
        if (str.length() == 1)
            return str.toLowerCase();
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String toCamelCase(String str) {
        if (StringUtils.isBlank(str))
            return str;
        if (str.length() == 1)
            return str.toUpperCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
