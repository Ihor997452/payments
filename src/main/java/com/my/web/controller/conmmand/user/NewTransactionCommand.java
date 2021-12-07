package com.my.web.controller.conmmand.user;

import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.AccountService;
import com.my.services.TransactionService;
import com.my.services.UserService;
import com.my.web.controller.util.Links;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

public class NewTransactionCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));


        long senderId = Long.parseLong(req.getParameter("account-sender").replaceAll(";.+", ""));
        long recipientId = Long.parseLong(req.getParameter("recipient"));
        Account sender = AccountService.get(senderId);
        Account recipient = AccountService.get(recipientId);

        BigDecimal amount = new BigDecimal(req.getParameter("amount"));
        String message = req.getParameter("message");
        if (message == null) {
            message = "";
        }

        if (this.validate(req, sender, amount, recipient, messages)) {
            if (TransactionService.doTransaction(sender, amount, recipient, message)) {
                session.setAttribute(MessageType.SUCCESS, messages.getTransactionSuccess());
            } else {
                session.setAttribute(MessageType.ERROR, messages.getTransactionError());
            }
        }

        resp.sendRedirect(Links.HOME);
    }
    private boolean validate(HttpServletRequest request, Account sender, BigDecimal amount, Account recipient, Messages messages) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if (user.getStatusId() == Status.BLOCKED.getValue()) {
            session.setAttribute(MessageType.ERROR, messages.getAccountDoesNotExistError());
            return false;
        }

        if (sender == null || recipient == null || amount == null || !UserService.hasAccount(user ,sender)) {
            session.setAttribute(MessageType.ERROR, messages.getBasicError());
            return false;
        }

        if (sender.getStatus() == Status.BLOCKED || recipient.getStatus() == Status.BLOCKED) {
            session.setAttribute(MessageType.ERROR, messages.getBasicError());
            return false;
        }

        if (sender.getId() == recipient.getId()) {
            //todo change message;
            session.setAttribute(MessageType.ERROR, messages.getAccountDoesNotExistError());
            return false;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0 || sender.getBalance().compareTo(amount) <= 0) {
            session.setAttribute(MessageType.ERROR, messages.getInvalidAmountError());
            return false;
        }

        if (!AccountService.exists(recipient.getId())) {
            session.setAttribute(MessageType.ERROR, messages.getRecipientDoesNotExistError());
            return false;
        }

        return true;
    }
}
