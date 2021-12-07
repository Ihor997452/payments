package com.my.db.constants;

public enum RequestType {
    UNBLOCK_USER,
    UNBLOCK_ACCOUNT;

    private RequestType() {
    }

    public static RequestType getType(int id) {
        return values()[id - 1];
    }

    public int getValue() {
        return this.ordinal() + 1;
    }
}
