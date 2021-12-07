package com.my.web.controller.conmmand;

import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.UserService;
import com.my.util.EmailSender;
import com.my.util.Security;
import com.my.web.controller.util.Links;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerifyCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));

        String verificationCode = "verificationCode";
        String s = "action";
        if (session.getAttribute(s) != null && session.getAttribute(verificationCode) != null) {
            String action = (String)session.getAttribute(s);
            int expectedCode = (int) session.getAttribute(verificationCode);

            int actualCode;
            try {
                actualCode = Integer.parseInt(req.getParameter("code"));
            } catch (NumberFormatException e) {
                req.getRequestDispatcher(Links.VERIFY_PAGE).forward(req, resp);
                return;
            }

            if (expectedCode == actualCode) {
                session.removeAttribute(s);
                session.removeAttribute(verificationCode);

                if (action.equalsIgnoreCase("register")) {
                    this.register(session, messages);
                }

                if (action.equalsIgnoreCase("restore")) {
                    this.restorePassword(session, messages);
                }

                if (action.equalsIgnoreCase("edit")) {
                    this.editProfile(session, messages);
                }

                resp.sendRedirect(Links.HOME);
                return;
            }

            req.setAttribute(MessageType.ERROR, messages.getIncorrectCodeError());
            req.getRequestDispatcher(Links.VERIFY_PAGE).forward(req, resp);
            return;
        }

        session.setAttribute(MessageType.ERROR, messages.getBasicError());
        resp.sendRedirect(Links.HOME);
    }

    private void register(HttpSession session, Messages messages) {
        User user = (User)session.getAttribute("userToRegister");
        session.removeAttribute("userToRegister");
        if (UserService.register(user)) {
            session.setAttribute(MessageType.SUCCESS, messages.getRegisterSuccess());
        } else {
            session.setAttribute(MessageType.ERROR, messages.getRegisterError());
        }
    }

    private void restorePassword(HttpSession session, Messages messages) {
        String newPassword = Security.generateNewPassword();
        String email = (String)session.getAttribute("email-restore");
        session.removeAttribute("email-restore");
        if (UserService.changePassword(email, newPassword)) {
            session.setAttribute(MessageType.SUCCESS, messages.getPasswordChangeSuccess());
            EmailSender.sendNewPassword(email, newPassword);
        } else {
            session.setAttribute(MessageType.ERROR, messages.getPasswordChangeError());
        }
    }

    private void editProfile(HttpSession session, Messages messages) {
        User user = (User)session.getAttribute("edit-user");
        if (UserService.edit(user)) {
            session.setAttribute("user", UserService.get(user.getId()));
            session.setAttribute(MessageType.SUCCESS, messages.getEditSuccess());
        } else {
            session.setAttribute(MessageType.ERROR, messages.getEditError());
        }

        session.removeAttribute("edit-user");
    }
}
