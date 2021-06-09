package com.acpreda.sprint10.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringHelperTest {

    @Test
    void should_normalizar() {
        assertEquals("AEIOUUN", StringHelper.normalizar("áéíóúüñ"));
        assertNull(StringHelper.normalizar(null));
        assertEquals("", StringHelper.normalizar(""));
    }

    @Test
    void should_snakeCase() {
        assertEquals("someSnakeStrings", StringHelper.toSnakeCase("SomeSnakeStrings"));
        assertEquals("someSnakeStrings", StringHelper.toSnakeCase("someSnakeStrings"));
        assertNull(StringHelper.toSnakeCase(null));
        assertEquals("", StringHelper.toSnakeCase(""));
        assertEquals("a", StringHelper.toSnakeCase("a"));
        assertEquals("a", StringHelper.toSnakeCase("A"));
    }

    @Test
    void should_camelCase() {
        assertEquals("SomeSnakeStrings", StringHelper.toCamelCase("SomeSnakeStrings"));
        assertEquals("SomeSnakeStrings", StringHelper.toCamelCase("someSnakeStrings"));
        assertNull(StringHelper.toCamelCase(null));
        assertEquals("", StringHelper.toCamelCase(""));
        assertEquals("A", StringHelper.toCamelCase("a"));
        assertEquals("A", StringHelper.toCamelCase("A"));
    }

}