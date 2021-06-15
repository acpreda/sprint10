package com.acpreda.sprint10.db.doc;

public class Database {

    private final Table[] tables;

    public Database(Table[] tables) {
        this.tables = tables;
    }

    public Table[] getTables() {
        return tables;
    }
}
