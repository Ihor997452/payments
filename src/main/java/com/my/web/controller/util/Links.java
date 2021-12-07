package com.my.web.controller.util;

import com.my.web.controller.conmmand.CommandNames;

public class Links {
    private Links() {

    }

    private static final String APP_COMMAND = "app?command=";
    public static final String HOME = APP_COMMAND + CommandNames.HOME;
    public static final String REGISTER = APP_COMMAND + CommandNames.REGISTER;
    public static final String VERIFY = APP_COMMAND + CommandNames.VERIFY;
    public static final String RESTORE = APP_COMMAND + CommandNames.RESTORE;
    public static final String NEW_ACCOUNT = APP_COMMAND + CommandNames.NEW_ACCOUNT;
    public static final String TOP_UP = APP_COMMAND + CommandNames.TOP_UP;
    public static final String USER_ACCOUNT = APP_COMMAND + CommandNames.VIEW_ACCOUNTS;
    public static final String VERIFY_PAGE = APP_COMMAND + CommandNames.VERIFY_PAGE;
    public static final String LOGIN = APP_COMMAND + CommandNames.LOGIN;
}
