package com.acpreda.sprint10.db.doc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleMetadataProvider extends MetadataProvider {

    private final String schema;

    public OracleMetadataProvider(DataSource dataSource, String schema) {
        super(dataSource);
        this.schema = schema;
    }

    @Override
    public Database getDatabase() {
        Table[] tables = getTables();
        return new Database(tables);
    }

    private Table[] getTables() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tablesResult = metaData.getTables(null, schema, "RUA_ADMIN%", null);
            List<Table> tables = new ArrayList<>();
            while (tablesResult.next()) {
                String tableName = tablesResult.getString(3);
                Column[] columns = getColumns(tableName);
                Table table = new Table(tableName, columns);
                tables.add(table);
            }
            tablesResult.close();
            return tables.toArray(new Table[0]);
        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private Column[] getColumns(String tableName) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columnsResult = metaData.getColumns(null, schema, tableName, null);
            List<Column> columns = new ArrayList<>();
            while (columnsResult.next()) {
                String columnName = columnsResult.getString(4);
                String type = columnsResult.getString(6);
                Column column = new Column(columnName, type);
                columns.add(column);
            }
            columnsResult.close();
            return columns.toArray(new Column[0]);
        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

}
