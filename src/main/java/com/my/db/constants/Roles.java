package com.my.db.constants;

public enum Roles {
    USER,
    ADMIN;

    private Roles() {
    }

    public static Roles getRole(int id) {
        return values()[id - 1];
    }

    public int getValue() {
        return this.ordinal() + 1;
    }

    public int getUser() {
        return USER.getValue();
    }

    public int getAdmin() {
        return ADMIN.getValue();
    }
}