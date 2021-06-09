package com.acpreda.sprint10.jpa.search;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PredicateBuilderTest {

    @Test
    void should_create_without_exceptions() {
        PredicateBuilder<SampleTestClass> withRoot = new PredicateBuilder<>(SampleTestClass.class);
        PredicateBuilder<SampleTestClass> withRootAndExpression = new PredicateBuilder<>(
                SampleTestClass.class,
                "stringProp:qwerty,intProp:1"
        );
        assertNotNull(withRoot);
        assertNotNull(withRootAndExpression);
    }

    @Test
    void should_build_when_string_criteria() throws InvalidPredicateException {
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "stringProp:VAL").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "stringProp~VAL").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "objectProp.stringProp:VAL").build());
        try {
            new PredicateBuilder<>(SampleTestClass.class,
                    "stringProp>VAL").build();
            fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    void should_build_when_string_norm_criteria() throws InvalidPredicateException {
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "stringNormProp:VAL").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "stringNormProp~VAL").build());
        try {
            new PredicateBuilder<>(SampleTestClass.class,
                    "stringNormProp>VAL").build();
            fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    void should_admit_date_criteria() throws InvalidPredicateException {
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "dateProp:2010-01-01").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "dateProp<2010-01-01").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "dateProp>2010-01-01").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "dateProp:" + new Date().getTime()).build());
        try {
            new PredicateBuilder<>(SampleTestClass.class,
                    "dateProp:NODATE").build();
            fail();
        } catch (Exception ignored) {
        }
        try {
            new PredicateBuilder<>(SampleTestClass.class,
                    "dateProp~2010-01-01").build();
            fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    void should_admit_number_criteria() throws InvalidPredicateException {
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "intProp:0").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "intProp<0").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "intProp>0").build());
        try {
            new PredicateBuilder<>(SampleTestClass.class,
                    "intProp:NAN").build();
            fail();
        } catch (Exception ignored) {
        }
        try {
            new PredicateBuilder<>(SampleTestClass.class,
                    "intProp~0").build();
            fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    void should_build_when_uuid_criteria() throws InvalidPredicateException {
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "uuidProp:E9F8E27C-52E8-4CD4-B3F5-9BA02C7D3EF2").build());
        try {
            assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                    "uuidProp>E9F8E27C-52E8-4CD4-B3F5-9BA02C7D3EF2").build());
            fail();
        } catch (Exception ignored) {
        }
        try {
            assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                    "uuidProp:VAL").build());
            fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    void should_build_when_boolean_criteria() throws InvalidPredicateException {
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "booleanProp:true").build());
        assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                "booleanProp:false").build());
        try {
            assertNotNull(new PredicateBuilder<>(SampleTestClass.class,
                    "booleanProp>true").build());
            fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    void should_admit_criteria() throws InvalidPredicateException {
        PredicateBuilder<SampleTestClass> withRoot = new PredicateBuilder<>(SampleTestClass.class);
        BooleanExpression booleanExpression = withRoot.build();
        assertNotNull(booleanExpression);
        String stringValue = booleanExpression.toString();
        System.out.println(stringValue);
    }

    @Test
    void should_build_without_expression() throws InvalidPredicateException {
        PredicateBuilder<SampleTestClass> noExpression = new PredicateBuilder<>(
                SampleTestClass.class, ""
        );
        BooleanExpression built = noExpression.build();
        assertNotNull(built);
        assertEquals("true = true", built.toString());
    }

    @Test
    void should_fail_when_property_not_exists() {
        PredicateBuilder<SampleTestClass> noProp = new PredicateBuilder<>(
                SampleTestClass.class, "noProp:1");
        try {
            noProp.build();
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }

        PredicateBuilder<SampleTestClass> noPropNoProp = new PredicateBuilder<>(
                SampleTestClass.class, "noProp.noProp:1");
        try {
            noPropNoProp.build();
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

}