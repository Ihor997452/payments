package com.my.web.controller.conmmand;

import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.UserService;
import com.my.web.controller.util.Links;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));

        String email = req.getParameter("email-login");
        String password = req.getParameter("password-login");
        User user = UserService.logIn(email, password);

        if (user != null) {
            session.setAttribute("user", user);
        } else {
            session.setAttribute(MessageType.ERROR, messages.getFindProfileError());
        }

        resp.sendRedirect(Links.HOME);
    }
}
