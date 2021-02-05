package com.acpreda.sprint10.inventory;

import java.math.BigDecimal;

public class Account {

    private final String code;
    private final String unit;
    private final boolean memo;
    private final boolean summary;
    private final BigDecimal balance;
    private final String name;
    private final Integer level;

    public Account(String code, String unit, boolean memo, boolean summary, BigDecimal balance, String name, Integer level) {
        this.code = code;
        this.unit = unit;
        this.memo = memo;
        this.summary = summary;
        this.balance = balance;
        this.name = name;
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isMemo() {
        return memo;
    }

    public boolean isSummary() {
        return summary;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public Integer getLevel() {
        return level;
    }
}
