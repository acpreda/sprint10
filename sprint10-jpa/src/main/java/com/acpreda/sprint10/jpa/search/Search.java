package com.acpreda.sprint10.jpa.search;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Search {
    SearchStrategy strategy();
}
