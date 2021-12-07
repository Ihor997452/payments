package com.my.db.constants;

public enum TransactionStatus {
    SUCCESSFUL,
    PENDING,
    FAILED;

    private TransactionStatus() {
    }

    public static TransactionStatus getStatus(int id) {
        return values()[id - 1];
    }

    public int getValue() {
        return this.ordinal() + 1;
    }
}
