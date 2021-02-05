package com.acpreda.sprint10.inventory;

import java.util.List;

public interface InventoryManager {

    Account account(String account);

    Account createAccount(String code, String unit, boolean memo, boolean summary, String name);

    Transaction saveTransaction(Transaction transaction);

    List<Account> getAccountsBySQL(String sql);

}
