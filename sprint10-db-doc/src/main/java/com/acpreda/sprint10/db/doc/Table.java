package com.acpreda.sprint10.db.doc;

public class Table {

    private final String name;
    private final String comments;
    private final Column[] columns;

    public Table(String name, String comments, Column[] columns) {
        this.name = name;
        this.comments = comments;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public String getComments() {
        return comments;
    }

    public Column[] getColumns() {
        return columns;
    }
}
