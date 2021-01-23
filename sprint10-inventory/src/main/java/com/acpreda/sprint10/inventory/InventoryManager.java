package com.acpreda.sprint10.inventory;

public interface InventoryManager {

    Account createAccount(String code, String unit, boolean memo, boolean summary, String name);

    Transaction saveTransaction(Transaction transaction);

}
