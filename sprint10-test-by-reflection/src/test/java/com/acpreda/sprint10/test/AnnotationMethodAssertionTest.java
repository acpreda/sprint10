package com.acpreda.sprint10.test;

import com.acpreda.sprint10.test.samples.TestingAnnotation;
import com.acpreda.sprint10.test.samples.AnnotatedClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationMethodAssertionTest {

    AnnotationMethodAssertion arrayValueAssertion;
    AnnotationMethodAssertion valueAssertion;

    @BeforeEach
    void beforeEach() throws NoSuchMethodException {
        TestingAnnotation testingAnnotation = AnnotatedClass.class.getAnnotation(TestingAnnotation.class);
        Method arrayValueMethod = testingAnnotation.getClass().getMethod("arrayValue");
        this.arrayValueAssertion = AnnotationMethodAssertion.of(testingAnnotation, arrayValueMethod);
        Method valueMethod = testingAnnotation.getClass().getMethod("value");
        this.valueAssertion = AnnotationMethodAssertion.of(testingAnnotation, valueMethod);
    }

    @DisplayName("Should pass with expected array value")
    @Test
    void should_pass_with_expected_array_value() {
        arrayValueAssertion.assertArrayEquals(new String[]{"TEXT2", "TEXT1"});
    }

    @DisplayName("Should fail with unexpected array value")
    @Test
    void should_fail_with_unexpected_array_value() {
        assertThrows(Throwable.class, () -> arrayValueAssertion.assertArrayEquals(new String[]{"TEXT3", "TEXT1"}));
    }

    @DisplayName("Should pass with expected value")
    @Test
    void should_pass_with_expected_value() {
        valueAssertion.assertEquals("SOMETHING");
    }

    @DisplayName("Should fail with unexpected value")
    @Test
    void should_fail_with_unexpected_value() {
        assertThrows(Throwable.class, () -> valueAssertion.assertEquals("NOTHING"));
        assertThrows(Throwable.class, () -> valueAssertion.assertEquals(null));
    }

}