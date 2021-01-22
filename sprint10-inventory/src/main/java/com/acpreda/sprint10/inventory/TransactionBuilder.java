package com.acpreda.sprint10.inventory;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public final class TransactionBuilder {

    private final String description;
    private final ZonedDateTime whenBooked;
    private final List<Entry> entries;

    private TransactionBuilder(ZoneId zoneId, String description) {
        this.entries = new ArrayList<>();
        this.whenBooked = ZonedDateTime.now(zoneId);
        this.description = description;
    }

    public static TransactionBuilder start(ZoneId zoneId, String description) {
        return new TransactionBuilder(zoneId, description);
    }

    public TransactionBuilder with(Account account, BigDecimal amount, ZonedDateTime whenCharged) {
        this.entries.add(new Entry(account, amount, whenCharged));
        return this;
    }

    public Transaction build() {
        return new Transaction(this.description, this.entries, this.whenBooked);
    }
}
