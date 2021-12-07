package com.my.web.controller.conmmand.user;

import com.my.db.constants.Currency;
import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.AccountService;
import com.my.services.TransactionService;
import com.my.web.controller.util.Links;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

public class TopUpCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));

        int topUpId = Integer.parseInt(req.getParameter("topUp-id"));
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("topUp-amount")));
        Currency currency = Currency.getCurrency(Integer.parseInt(req.getParameter("topUp-currency")));
        Account account = AccountService.get(topUpId);

        if (account != null && currency != null && amount.compareTo(BigDecimal.ZERO) >= 0) {
            if (account.getStatus() == Status.BLOCKED) {
                session.setAttribute(MessageType.ERROR, messages.getBasicError());
                resp.sendRedirect(Links.HOME);
                return;
            }

            if (TransactionService.doTopUp(amount, account, currency)) {
                session.setAttribute(MessageType.SUCCESS, messages.getTopUpSuccess());
            } else {
                session.setAttribute(MessageType.ERROR, messages.getTopUpError());
            }
        } else {
            session.setAttribute(MessageType.ERROR, messages.getBasicError());
        }

        resp.sendRedirect(Links.HOME);
    }
}
