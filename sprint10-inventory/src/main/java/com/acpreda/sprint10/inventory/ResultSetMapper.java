package com.acpreda.sprint10.inventory;

import java.sql.ResultSet;

public interface ResultSetMapper<T> {

    T map(ResultSet rs, int index);

}
