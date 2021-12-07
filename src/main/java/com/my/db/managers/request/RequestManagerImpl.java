package com.my.db.managers.request;

public class RequestManagerImpl extends AbstractRequestManager {
    private static RequestManagerImpl instance;

    private RequestManagerImpl() {
    }

    public static synchronized RequestManagerImpl getInstance() {
        if (instance == null) {
            instance = new RequestManagerImpl();
        }

        return instance;
    }
}

