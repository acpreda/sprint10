package com.acpreda.sprint10.inventory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcInventoryManager extends JdbcSupport implements InventoryManager {

    private final Map<String, Account> accounts = new HashMap<>();

    public JdbcInventoryManager(DataSource dataSource) {
        super(dataSource);
        cacheAccounts();
    }

    private void cacheAccounts() {
        String sql = "" +
                "select account, unit, memo, summary, balance, name " +
                "  from account";
        List<Account> accountList = super.select(sql, new Object[0], (rs, i) -> {
            try {
                return new Account(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getBoolean(3),
                        rs.getBoolean(4),
                        rs.getBigDecimal(5),
                        rs.getString(6)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error finding account", e);
            }
        });
        this.accounts.clear();
        for (var account : accountList) {
            this.accounts.put(account.getCode(), account);
        }
    }

    @Override
    public Account createAccount(String code, String unit, boolean memo, boolean summary, String name) {
        var account = new Account(code, unit, memo, summary, BigDecimal.ZERO, name);
        Account foundAccount = account(code);
        if (foundAccount != null) {
            throw new IllegalArgumentException("Account code duplicated");
        }
        if (unit == null || unit.trim().equals("")) {
            throw new IllegalArgumentException("Unit is blank");
        }
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("Name is blank");
        }
        insert(account);
        return account;
    }

    private void insert(Account account) {
        String sql = "" +
                "insert into account (account, unit, memo, summary, balance, name) " +
                "values (?, ?, ?, ?, ?, ?)";
        super.insert(sql, new Object[][]{new Object[]{
                account.getCode(),
                account.getUnit(),
                account.isMemo(),
                account.isSummary(),
                account.getBalance(),
                account.getName()
        }});
    }

    @Override
    public Account account(String code) {
        String sql = "" +
                "select account, unit, memo, summary, balance, name " +
                "  from account where account = ?";

        var accountList = super.select(sql, new Object[]{code}, (rs, i) -> {
            try {
                return new Account(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getBoolean(3),
                        rs.getBoolean(4),
                        rs.getBigDecimal(5),
                        rs.getString(6)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error finding account", e);
            }
        });

        if (accountList.size() > 1) {
            throw new IllegalStateException("Existen varias cuentas con el mismo identificador");
        } else if (accountList.size() == 0) {
            return null;
        } else {
            return accountList.get(0);
        }
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        String sqlTx = "insert into tx (tx, when_booked, description) values (?,?,?)";
        long tx = ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
        insert(sqlTx, new Object[][]{new Object[]{
                tx,
                transaction.getWhenBooked(),
                transaction.getDescription()
        }});
        String sqlEntry = "insert into entry (tx, account, when_charged, amount) values (?,?,?,?)";
        Object[][] params = new Object[transaction.getEntries().size()][];
        int i = 0;
        for (var entry : transaction.getEntries()) {
            params[i++] = new Object[]{tx, entry.getAccount().getCode(), entry.getWhenCharged(), entry.getAmount()};
        }
        insert(sqlEntry, params);
        return transaction;
    }

    @Override
    public List<Account> getAccountsBySQL(String sql) {
        //String sql = "select account, unit, memo, summary, balance, name from account";
        var accountList = super.select(sql, new Object[]{}, (rs, i) -> {
            try {
                return new Account(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getBoolean(3),
                        rs.getBoolean(4),
                        rs.getBigDecimal(5),
                        rs.getString(6)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error finding account", e);
            }
        });

        if (accountList.size() == 0) {
            return null;
        } else {
            return accountList;
        }
    }
}
