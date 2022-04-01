package com.acpreda.sprint10.db.doc;

import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MssqlMetadataProvider extends MetadataProvider {

    private final String schema;
    private final String[] tableFilters;

    public MssqlMetadataProvider(DataSource dataSource, String schema, String[] tableFilters) {
        super(dataSource);
        this.schema = schema;
        this.tableFilters = tableFilters;
    }

    @Override
    public Database getDatabase() {
        Table[] tables = getTables();
        return new Database(tables);
    }

    @Override
    public String updateCommentsScript(Database database) {
        StringBuilder sb = new StringBuilder();
        for(Table table : database.getTables()) {
            if(StringUtils.isBlank(table.getComments())) {
                sb.append("exec sp_addextendedproperty\n" +
                        "     @name = N'MS_Description' \n" +
                        "    ,@value = '' \n" +
                        "    ,@level0type = N'Schema', @level0name = 'dbo' \n" +
                        "    ,@level1type = N'Table',  @level1name = '").append(table.getName()).append("'\nGO\n");
            }
            for(Column column  : table.getColumns()) {
                if(StringUtils.isBlank(column.getComments())) {
                    sb.append("exec sp_addextendedproperty\n" +
                            "     @name = N'MS_Description' \n" +
                            "    ,@value = '' \n" +
                            "    ,@level0type = N'Schema', @level0name = 'dbo' \n" +
                            "    ,@level1type = N'Table',  @level1name = '").append(table.getName()).append("'\n" +
                            "    ,@level2type = N'Column', @level2name = '").append(column.getName()).append("'\n" +
                            "GO\n");
                }
            }
        }
        return sb.toString();
    }

    private Table[] getTables() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            List<Table> tables = new ArrayList<>();
            for (String tableFilter : tableFilters) {
                ResultSet tablesResult = metaData.getTables(null, schema, tableFilter, null);

                String tableCommentSql = "" +
                        "select t.name, p.value " +
                        "  from sys.tables t " +
                        "  left outer join sys.extended_properties p  on t.object_id = p.major_id and p.minor_id = 0 and p.name = 'MS_Description' " +
                        " where t.name =  ? ";
                PreparedStatement stmt = connection.prepareStatement(tableCommentSql);

                while (tablesResult.next()) {
                    String tableName = tablesResult.getString(3);
                    Column[] columns = getColumns(tableName);

                    String tableComment = null;
                    stmt.setString(1, tableName);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        tableComment = rs.getString(2);
                    }

                    Table table = new Table(tableName, tableComment, columns);
                    tables.add(table);
                }
                tablesResult.close();
            }
            return tables.toArray(new Table[0]);
        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private Column[] getColumns(String tableName) {

        String sql = "SELECT object_name(c.object_id) " +
                "     , c.column_id " +
                "     , c.name AS Field " +
                "     , c.is_nullable " +
                "     , concat(t.name, case when c.scale != 0 then concat('(', c.precision, ',', c.scale, ')') " +
                "                           when c.precision != 0 then concat('(', c.precision, ')') " +
                "                           when c.max_length != 0 and t.name like '%CHAR%' then concat('(', c.max_length,')') " +
                "                        end " +
                "              ) as DATA_TYPE " +
                "     , p.value " +
                "FROM sys.columns AS c " +
                "JOIN sys.types AS t ON t.system_type_id = c.system_type_id " +
                "left outer JOIN sys.extended_properties as p on p.major_id = c.object_id and p.minor_id = c.column_id and p.name = 'MS_Description' " +
                "WHERE object_id = object_id(?) " +
                "  and t.name != 'sysname' " +
                "ORDER BY column_id";

        try (Connection connection = dataSource.getConnection()) {
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
        }
    }

}
