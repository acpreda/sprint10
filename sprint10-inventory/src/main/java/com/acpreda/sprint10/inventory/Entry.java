package com.acpreda.sprint10.inventory;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Entry {

    private final Account account;
    private final BigDecimal amount;
    private final ZonedDateTime whenCharged;
    private Transaction transaction;

    Entry(Account account, BigDecimal amount, ZonedDateTime whenCharged) {
        if (account == null) {
            throw new IllegalArgumentException("Account is null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount is null");
        }
        if (BigDecimal.ZERO.equals(amount)) {
            throw new IllegalArgumentException("Amount is zero");
        }
        if (whenCharged.isAfter(ZonedDateTime.now(whenCharged.getZone()))) {
            throw new IllegalArgumentException("Charge date is future");
        }
        this.account = account;
        this.amount = amount;
        this.whenCharged = whenCharged;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ZonedDateTime getWhenCharged() {
        return whenCharged;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
