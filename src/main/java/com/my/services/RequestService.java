package com.my.services;

import com.my.db.constants.RequestStatus;
import com.my.db.constants.RequestType;
import com.my.db.entity.Account;
import com.my.db.entity.Request;
import com.my.db.entity.User;
import com.my.db.managers.Manager;
import com.my.db.managers.request.RequestManager;
import com.my.db.managers.request.RequestManagerImpl;
import com.my.util.StackTrace;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RequestService extends AbstractService<Request> {
    private static final RequestManager REQUEST_MANAGER = RequestManagerImpl.getInstance();
    private static final RequestService instance = new RequestService();

    private RequestService() {
    }

    @Override
    protected Manager<Request> getManager() {
        return REQUEST_MANAGER;
    }

    public static Request get(long id) {
        return instance.abstractGet(id);
    }

    public static List<Request> getAll() {
        return instance.abstractGetAll();
    }

    public static List<Request> getAll(Comparator<Request> comparator) {
        return instance.abstractGetAll(comparator);
    }

    public static List<Request> search(String s) {
        return instance.abstractSearch(s);
    }

    public static boolean send(Request request) {
        return instance.abstractInsert(request);
    }

    public static boolean approve(Request request) {
        try {

            if (request.getRequestType() == RequestType.UNBLOCK_ACCOUNT) {
                Account account = AccountService.get(request.getRequesterId());

                if (account == null) {
                    return false;
                }

                AccountService.unblock(account);
            }

            if (request.getRequestType() == RequestType.UNBLOCK_USER) {
                User user = UserService.get(request.getRequesterId());

                if (user == null) {
                    return false;
                }

                UserService.unblock(user);
            }

            request.setRequestStatus(RequestStatus.APPROVED);
            REQUEST_MANAGER.update(request);
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }

    public static boolean disapprove(Request request) {
        try {
            request.setRequestStatus(RequestStatus.DISAPPROVED);
            REQUEST_MANAGER.update(request);
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }
}
