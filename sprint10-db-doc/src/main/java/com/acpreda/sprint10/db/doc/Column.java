package com.acpreda.sprint10.db.doc;

public class Column {

    private final String name;
    private final String type;
    private final String comments;

    public Column(String name, String type, String comments) {
        this.name = name;
        this.type = type;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getComments() {
        return comments;
    }
}
