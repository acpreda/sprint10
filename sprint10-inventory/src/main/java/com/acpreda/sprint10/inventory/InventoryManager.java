package com.acpreda.sprint10.inventory;

public interface InventoryManager {

    void createAccount(String tenant, String code, String unit, boolean memo, boolean summary, String name);

    void saveTransaction(Transaction transaction);

}
