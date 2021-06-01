package com.acpreda.sprint10.test.samples;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TestingAnnotation {

    String value() default "";

    String[] arrayValue() default "";

}
