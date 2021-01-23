package com.acpreda.sprint10.inventory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcSupport {

    private final DataSource dataSource;

    public JdbcSupport(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected void insert(String sql, Object[][] params) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql)) {
            for (Object[] array : params) {
                setParams(pStatement, array);
                pStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error inserting", e);
        }
    }

    protected <T> List<T> select(String sql, Object[] params, ResultSetMapper<T> mapper) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql)) {
            setParams(pStatement, params);
            ResultSet resultSet = pStatement.executeQuery();
            int index = 0;
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapper.map(resultSet, index++));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Error selecting", e);
        }
    }

    private void setParams(PreparedStatement pStatement, Object[] params) throws Exception {
        int i = 0;
        for (Object p : params) {
            if (p instanceof String) {
                pStatement.setString(i + 1, (String) p);
            } else if (p instanceof BigDecimal) {
                pStatement.setBigDecimal(i + 1, (BigDecimal) p);
            } else if (p instanceof Long) {
                pStatement.setLong(i + 1, (Long) p);
            } else if (p instanceof Boolean) {
                pStatement.setBoolean(i + 1, (Boolean) p);
            } else if (p instanceof ZonedDateTime) {
                OffsetDateTime oft = OffsetDateTime.ofInstant(((ZonedDateTime) p).toInstant(), ((ZonedDateTime) p).getZone());
                pStatement.setObject(i + 1, oft);
            } else {
                throw new IllegalArgumentException("Parameter type " + p.getClass() + " at index " + i + " not supported");
            }
            i++;
        }
    }

}
