package com.my.db.constants;

public enum Currency {
    UAH,
    USD,
    EUR,
    RUB;

    private Currency() {
    }

    public static Currency getCurrency(int id) {
        return values()[id - 1];
    }

    public int getValue() {
        return this.ordinal() + 1;
    }
}
