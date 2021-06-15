package com.acpreda.sprint10.db.doc;

import javax.sql.DataSource;
import java.sql.*;
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
            ResultSet tablesResult = metaData.getTables(null, schema, "RUA_UBIC%", null);
            List<Table> tables = new ArrayList<>();

            String tableCommentSql = "" +
                    "select table_name, comments " +
                    "  from user_tab_comments " +
                    " where table_name = ?";
            PreparedStatement stmt = connection.prepareStatement(tableCommentSql);

            while (tablesResult.next()) {
                String tableName = tablesResult.getString(3);
                Column[] columns = getColumns(tableName);

                String tableComment = null;
                stmt.setString(1, tableName);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    tableComment = rs.getString(2);
                }

                Table table = new Table(tableName, tableComment, columns);
                tables.add(table);
            }
            tablesResult.close();

            for(Table table : tables) {

            }
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

        String sql = "" +
                "select TABLE_NAME, " +
                "  K.COLUMN_ID, COLUMN_NAME, " +
                "  K.NULLABLE, K.DATA_TYPE ||  " +
                "    case when K.DATA_SCALE is not null then '(' || K.DATA_PRECISION || ',' || K.DATA_SCALE || ')' " +
                "      when K.DATA_PRECISION is not null then '(' || K.DATA_PRECISION || ')' " +
                "      when K.DATA_LENGTH is not null and K.DATA_TYPE like '%CHAR%' then '(' || K.DATA_LENGTH || ')' " +
                "    end DATA_TYPE, " +
                "  C.COMMENTS " +
                "from user_col_comments C join user_tab_cols K " +
                "using(TABLE_NAME,COLUMN_NAME) " +
                "where table_name = ? " +
                "order by TABLE_NAME, K.COLUMN_ID";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tableName);
            ResultSet columnsResult = stmt.executeQuery();
            List<Column> columns = new ArrayList<>();
            while (columnsResult.next()) {
                String columnName = columnsResult.getString(3);
                String type = columnsResult.getString(5);
                String comments = columnsResult.getString(6);
                Column column = new Column(columnName, type, comments);
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
