package com.acpreda.sprint10.inventory;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public class Transaction {

    private final Long tx;
    private final String description;
    private final ZonedDateTime whenBooked;
    private final List<Entry> entries;

    Transaction(Long tx, String description, List<Entry> entries, ZonedDateTime whenBooked) {
        if (tx == null || tx <= 0L) {
            throw new IllegalArgumentException("Transaction identifier not valid");
        }
        if (entries == null || entries.size() < 2) {
            throw new IllegalArgumentException("At least 2 entries are required for a transaction");
        }
        if (!isBalanced(entries)) {
            throw new IllegalArgumentException("Entries in transaction are not balanced");
        }
        this.entries = entries;
        for (var entry : entries) {
            entry.setTransaction(this);
        }
        this.tx = tx;
        this.whenBooked = whenBooked;
        this.description = description;
    }

    private static boolean isBalanced(List<Entry> entries) {
        BigDecimal balance = BigDecimal.ZERO;
        for (var entry : entries) {
            if (!entry.getAccount().isMemo()) {
                balance = balance.add(entry.getAmount());
            }
        }
        return balance.compareTo(BigDecimal.ZERO) == 0;
    }

    public Long getTx() {
        return tx;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getWhenBooked() {
        return whenBooked;
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
