package com.my.web.controller.conmmand.user;

import com.my.db.constants.RequestStatus;
import com.my.db.constants.RequestType;
import com.my.db.entity.Account;
import com.my.db.entity.Request;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.AccountService;
import com.my.services.RequestService;
import com.my.util.StackTrace;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SendRequestCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Messages messages = new Messages((String) session.getAttribute("lang"));
        RequestType requestType = getRequestType(req.getParameter("request-type"));

        if (requestType == null) {
            session.setAttribute(MessageType.ERROR, messages.getBasicError());
            resp.sendRedirect(req.getHeader("referer"));
            return;
        }

        Request request = getRequest(requestType);

        if (requestType == RequestType.UNBLOCK_ACCOUNT) {
            long accountId = getRequesterId(req, user);

            if (accountId == -1) {
                session.setAttribute(MessageType.ERROR, messages.getBasicError());
                resp.sendRedirect(req.getHeader("referer"));
                return;
            }

            request.setRequesterId(accountId);
        } else {
            request.setRequesterId(user.getId());
        }

        if (RequestService.getAll().contains(request)) {
            session.setAttribute(MessageType.INFO, messages.getBasicError());
            resp.sendRedirect(req.getHeader("referer"));
            return;
        }

        if (RequestService.send(request)) {
            session.setAttribute(MessageType.SUCCESS, messages.getBasicError());
        } else {
            session.setAttribute(MessageType.ERROR, messages.getBasicError());
        }
        resp.sendRedirect(req.getHeader("referer"));
    }

    private long getRequesterId(HttpServletRequest request, User user) {
        try {
            String accountId = request.getParameter("account");
            long id = Long.parseLong(accountId);

            Account account = AccountService.get(id);
            if (account == null || !AccountService.userOwnsAccount(user, account)) {
                return -1;
            }

            return id;
        } catch (NumberFormatException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            return -1;
        }
    }

    private RequestType getRequestType(String  requestType) {
        try {
            int req = Integer.parseInt(requestType);

            if (req != 1 && req != 2) {
                return null;
            }

            return RequestType.getType(req);
        } catch (NumberFormatException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            return null;
        }
    }

    private Request getRequest(RequestType requestType) {
        Request request = new Request();
        request.setRequestType(requestType);
        request.setTime(Timestamp.valueOf(LocalDateTime.now()));
        request.setRequestStatus(RequestStatus.PENDING);

        return request;
    }
}
