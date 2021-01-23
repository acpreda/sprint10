package com.acpreda.sprint10.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InMemoryInventoryManager implements InventoryManager {

    private final List<Account> accounts = new ArrayList<>();
    private final List<Entry> entries = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void createAccount(String code, String unit, boolean memo, boolean summary, String name) {
        var account = new Account(code, unit, memo, summary, BigDecimal.ZERO, name);
        if (accounts.stream().anyMatch(x -> x.getCode().equals(code))) {
            throw new IllegalArgumentException("Account code duplicated");
        }
        if (unit == null || unit.trim().equals("")) {
            throw new IllegalArgumentException("Unit is blank");
        }
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("Name is blank");
        }
        this.accounts.add(account);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        this.entries.addAll(transaction.getEntries());
        this.transactions.add(transaction);
    }
}
