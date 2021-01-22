package com.acpreda.sprint10.inventory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class JdbcInventoryManager extends JdbcSupport implements InventoryManager {

    public JdbcInventoryManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void createAccount(String tenant, String code, String unit, boolean memo, boolean summary, String name) {
        var account = new Account(tenant, code, unit, memo, summary, BigDecimal.ZERO, name);
        List<Account> foundAccounts = findAccountByCode(tenant, code);
        if (foundAccounts.size() > 0) {
            throw new IllegalArgumentException("Account code duplicated");
        }
        if (tenant == null || tenant.trim().equals("")) {
            throw new IllegalArgumentException("Tenant is blank");
        }
        if (unit == null || unit.trim().equals("")) {
            throw new IllegalArgumentException("Unit is blank");
        }
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("Name is blank");
        }
        insert(account);
    }

    private void insert(Account account) {
        String sql = "" +
                "insert into account (tenant, code, unit, memo, summary, balance, name) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        super.insert(sql, new Object[][]{new Object[]{
                account.getTenant(),
                account.getCode(),
                account.getUnit(),
                account.isMemo(),
                account.isSummary(),
                account.getBalance(),
                account.getName()
        }});
    }

    private List<Account> findAccountByCode(String tenant, String code) {
        String sql = "" +
                "select tenant, code, unit, memo, summary, balance, name " +
                "  from account where tenant = ? and code = ?";
        return super.select(sql, new Object[]{tenant, code}, (rs, i) -> {
            try {
                return new Account(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4),
                        rs.getBoolean(5),
                        rs.getBigDecimal(6),
                        rs.getString(7)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error finding account", e);
            }
        });
    }

    @Override
    public void saveTransaction(Transaction transaction) {

    }
}
