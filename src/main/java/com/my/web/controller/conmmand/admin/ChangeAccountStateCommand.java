package com.my.web.controller.conmmand.admin;

import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeAccountStateCommand extends AbstractChangeStateCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));

        long id = getId(req);

        if (id == 0L) {
            session.setAttribute(MessageType.ERROR, messages.getBasicError());
        } else {
            //todo: change success message
            if (AccountService.changeState(id)) {
                session.setAttribute(MessageType.SUCCESS, messages.getBasicError());
            } else {
                session.setAttribute(MessageType.ERROR, messages.getBasicError());
            }
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
