package com.my.web.controller.conmmand.user;

import com.my.db.constants.Status;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.util.EmailSender;
import com.my.util.Security;
import com.my.util.Validator;
import com.my.web.controller.util.Links;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditProfileCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));
        User user = (User) session.getAttribute("user");

        String newEmail = req.getParameter("email-edit");
        String newName = req.getParameter("name-edit");
        String newSurname = req.getParameter("surname-edit");
        String newPassword = req.getParameter("password-edit");

        if (user.getStatusId() == Status.BLOCKED.getValue()) {
            //todo: change message
            session.setAttribute(MessageType.ERROR, messages.getInvalidInputError());
            resp.sendRedirect(Links.HOME);
            return;
        }

        if (!Validator.validateEmail(newEmail) && !Validator.validatePassword(newPassword)) {
            session.setAttribute(MessageType.ERROR, messages.getInvalidInputError());
            resp.sendRedirect(Links.HOME);
        } else {
            User editedUser = (User)session.getAttribute("user");
            editedUser.setEmail(newEmail);
            editedUser.setName(newName);
            editedUser.setSurname(newSurname);
            byte[] salt = Security.getSalt();
            editedUser.setPassword(Security.encrypt(req.getParameter("password-edit"), salt));
            editedUser.setSalt(Security.toHexString(salt));
            int code = Security.generateVerificationCode();
            EmailSender.sendVerificationCode(editedUser.getEmail(), code);

            session.setAttribute("edit-user", editedUser);
            session.setAttribute("action", "edit");
            session.setAttribute("verificationCode", code);
            resp.sendRedirect(Links.VERIFY_PAGE);
        }
    }
}
