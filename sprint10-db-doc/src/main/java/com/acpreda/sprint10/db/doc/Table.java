package com.acpreda.sprint10.db.doc;

public class Table {

    private final String name;
    private final Column[] columns;

    public Table(String name, Column[] columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public Column[] getColumns() {
        return columns;
    }
}
