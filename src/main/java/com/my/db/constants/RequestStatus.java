package com.my.db.constants;

public enum RequestStatus {
    APPROVED,
    PENDING,
    DISAPPROVED;

    private RequestStatus() {
    }

    public static RequestStatus getStatus(int id) {
        return values()[id - 1];
    }

    public int getValue() {
        return this.ordinal() + 1;
    }
}
