package com.my.db.constants;

public enum Status {
    ACTIVE,
    BLOCKED;

    private Status() {
    }

    public static Status getStatus(int id) {
        return values()[id - 1];
    }

    public int getValue() {
        return this.ordinal() + 1;
    }

    public int getActive() {
        return ACTIVE.getActive();
    }

    public int getBlocked() {
        return BLOCKED.getValue();
    }
}

