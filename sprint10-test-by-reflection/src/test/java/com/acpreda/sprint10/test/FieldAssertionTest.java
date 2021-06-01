package com.acpreda.sprint10.test;

import com.acpreda.sprint10.test.samples.NoSetClass;
import com.acpreda.sprint10.test.samples.TestingAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class FieldAssertionTest {

    @DisplayName("Should pass annotated field")
    @Test
    void should_pass_annotated_field() throws NoSuchFieldException {
        Field field = NoSetClass.class.getDeclaredField("attrib");
        FieldAssertion.of(field).isAnnotated(TestingAnnotation.class);
    }

}