package com.my.web.controller.conmmand.user;

import com.my.db.constants.Currency;
import com.my.db.constants.Status;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.AccountService;
import com.my.web.controller.util.Links;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class NewAccountCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));

        User user = (User)session.getAttribute("user");
        String accountName = req.getParameter("new-account-name");

        if (user.getStatusId() == Status.BLOCKED.getValue()) {
            //todo: change message
            session.setAttribute(MessageType.ERROR, messages.getInvalidInputError());
            resp.sendRedirect(Links.HOME);
            return;
        }

        if (accountName != null) {
            accountName = accountName.trim();
        }

        if (Objects.equals(accountName, "")) {
            accountName = "MyAccount";
        }

        Currency accountCurrency = Currency.getCurrency(Integer.parseInt(req.getParameter("new-account-currency")));

        if (AccountService.createAccountForUser(user, accountCurrency, accountName)) {
            session.setAttribute(MessageType.SUCCESS, messages.getAccountCreateSuccess());
        } else {
            session.setAttribute(MessageType.ERROR, messages.getAccountCreateError());
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
