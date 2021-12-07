package com.my.web.controller.conmmand.user;

import com.my.db.constants.Currency;
import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.AccountService;
import com.my.util.CurrencyConverter;
import com.my.util.StackTrace;
import com.my.web.controller.util.Links;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditAccountCommand extends Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String) session.getAttribute("lang"));
        User user = (User) session.getAttribute("user");
        String name = req.getParameter("new-name");

        Currency currency;
        Account account;
        int pinCode;
        try {
            long id = Integer.parseInt(req.getParameter("id"));
            pinCode = Integer.parseInt(req.getParameter("pin-code"));
            account = AccountService.get(id);
            currency = Currency.getCurrency(Integer.parseInt(req.getParameter("new-currency")));
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            log.error(StackTrace.getStackTrace(e));
            session.setAttribute(MessageType.ERROR, messages.getEditError());
            resp.sendRedirect(Links.HOME);
            return;
        }

        if (account == null || !AccountService.userOwnsAccount(user, account)) {
            session.setAttribute(MessageType.ERROR, messages.getAccountDoesNotExistError());
            resp.sendRedirect(Links.HOME);
            return;
        }

        String s = "&account=";
        if (account.getStatus() == Status.BLOCKED) {
            //todo: change message
            session.setAttribute(MessageType.ERROR, messages.getPinCodeError());
            resp.sendRedirect(Links.USER_ACCOUNT + s + account.getId());
            return;
        }

        if (pinCode != account.getPinCode()) {
            session.setAttribute(MessageType.ERROR, messages.getPinCodeError());
            resp.sendRedirect(Links.USER_ACCOUNT + s + account.getId());
            return;
        }

        if (currency == null || currency.getValue() == 0) {
            session.setAttribute(MessageType.ERROR, messages.getCurrencyError());
            resp.sendRedirect(Links.USER_ACCOUNT + s + account.getId());
            return;
        }

        account.setName(name);
        account.setBalance(CurrencyConverter.convert(account.getCurrency(), currency, account.getBalance()));
        account.setCurrency(currency);
        if (AccountService.edit(account)) {
            session.setAttribute(MessageType.SUCCESS, messages.getEditSuccess());
        } else {
            session.setAttribute(MessageType.ERROR, messages.getEditError());
        }

        resp.sendRedirect(Links.USER_ACCOUNT + s + account.getId());
    }
}
