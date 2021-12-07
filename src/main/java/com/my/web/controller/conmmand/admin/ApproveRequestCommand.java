package com.my.web.controller.conmmand.admin;

import com.my.db.constants.RequestType;
import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.Request;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.services.AccountService;
import com.my.services.RequestService;
import com.my.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ApproveRequestCommand extends AbstractRequestCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Request request = super.getRequest(req);
        if (request == null) {
            resp.sendRedirect(req.getHeader("referer"));
            return;
        }

        if (RequestService.approve(request)) {
            session.setAttribute(MessageType.SUCCESS, "approved");
        } else {
            session.setAttribute(MessageType.ERROR, "could not approve");
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
