package com.my.web.controller.conmmand.admin;

import com.my.db.constants.RequestType;
import com.my.db.entity.Request;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.services.AccountService;
import com.my.services.RequestService;
import com.my.services.UserService;
import com.my.util.EmailSender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DisapproveRequestCommand extends AbstractRequestCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Request request = super.getRequest(req);
        if (request == null) {
            resp.sendRedirect(req.getHeader("referer"));
            return;
        }


        if (RequestService.disapprove(request)) {
            session.setAttribute(MessageType.SUCCESS, "Request DisApprove Successfully");

            User user;
            if (request.getRequestType() == RequestType.UNBLOCK_ACCOUNT) {
                user = AccountService.getOwner(AccountService.get(request.getRequesterId()));
            } else {
                user = UserService.get(request.getRequesterId());
            }

            EmailSender.sendRequestDisapprovedMessage(user.getEmail(), request);
        } else {
            session.setAttribute(MessageType.ERROR, "Could not disapprove request");
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
