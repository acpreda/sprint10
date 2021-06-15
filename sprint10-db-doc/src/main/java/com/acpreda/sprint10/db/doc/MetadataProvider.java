package com.acpreda.sprint10.db.doc;

import javax.sql.DataSource;

public abstract class MetadataProvider {

    protected final DataSource dataSource;

    public MetadataProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public abstract Database getDatabase();

    public abstract String updateCommentsScript(Database database);

}
