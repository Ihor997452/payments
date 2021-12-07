package com.my.web.controller.conmmand;

import com.my.db.constants.Roles;
import com.my.db.constants.Status;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.UserService;
import com.my.util.EmailSender;
import com.my.util.Security;
import com.my.util.Validator;
import com.my.web.controller.util.Links;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String) session.getAttribute("lang"));
        String email = req.getParameter("email-register");

        if (!requestIsValid(req)) {
            session.setAttribute(MessageType.ERROR, messages.getInvalidInputError());
            resp.sendRedirect(Links.HOME);
            return;
        } else if (UserService.existsByEmail(email)) {
            session.setAttribute(MessageType.ERROR, messages.getUserAlreadyExistsError());
            resp.sendRedirect(Links.HOME);
            return;
        }

        User userToRegister = this.createInstance(req);
        int code = Security.generateVerificationCode();

        session.setAttribute("action", "register");
        session.setAttribute("userToRegister", userToRegister);
        session.setAttribute("verificationCode", code);

        EmailSender.sendVerificationCode(email, code);

        resp.sendRedirect(Links.VERIFY_PAGE);
    }

    private User createInstance(HttpServletRequest req) {
        User user = new User();
        byte[] salt = Security.getSalt();
        user.setEmail(req.getParameter("email-register"));
        user.setName(req.getParameter("name-register"));
        user.setSurname(req.getParameter("surname-register"));
        user.setPassword(Security.encrypt(req.getParameter("password-register"), salt));
        user.setSalt(Security.toHexString(salt));
        user.setRoleId(Roles.USER);
        user.setStatusId(Status.ACTIVE);
        return user;
    }

    private boolean requestIsValid(HttpServletRequest req) {
        String email = req.getParameter("email-register");
        String password = req.getParameter("password-register");
        String passwordToConfirm = req.getParameter("password-register-confirm");
        return Validator.validateEmail(email) && Validator.validatePassword(password, passwordToConfirm);
    }
}
