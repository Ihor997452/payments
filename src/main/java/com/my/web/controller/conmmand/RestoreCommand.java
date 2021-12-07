package com.my.web.controller.conmmand;

import com.my.messages.Messages;
import com.my.services.UserService;
import com.my.util.EmailSender;
import com.my.util.Security;
import com.my.web.controller.util.Links;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RestoreCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));

        String email = req.getParameter("email-restore");
        int code = Security.generateVerificationCode();

        if (UserService.existsByEmail(email)) {
            session.setAttribute("action", "restore");
            session.setAttribute("verificationCode", code);
            session.setAttribute("email-restore", email);
            EmailSender.sendVerificationCode(email, code);
            resp.sendRedirect(Links.VERIFY_PAGE);
        } else {
            session.setAttribute("error", messages.getFindProfileError());
            resp.sendRedirect(Links.HOME);
        }
    }
}
