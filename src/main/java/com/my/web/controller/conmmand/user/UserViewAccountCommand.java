package com.my.web.controller.conmmand.user;

import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.AccountService;
import com.my.util.StackTrace;
import com.my.web.controller.util.Links;
import com.my.web.controller.util.Path;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserViewAccountCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String) session.getAttribute("lang"));

        User user = (User) session.getAttribute("user");
        String accountId = req.getParameter("account");

        if (accountId == null || user.getStatusId() == Status.BLOCKED.getValue()) {
            resp.sendRedirect(Links.HOME);
            return;
        }

        Account account;
        try {
            long id = Long.parseLong(accountId);
            account = AccountService.get(id);
        } catch (NumberFormatException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            resp.sendRedirect(Links.HOME);
            return;
        }

        if (!AccountService.userOwnsAccount(user, account)) {
            resp.sendRedirect(Links.HOME);
            return;
        }

        if (account.getStatus() == Status.BLOCKED) {
            session.setAttribute(MessageType.INFO, messages.getBlockedAccountInfo());
        }

        req.setAttribute("account", account);
        req.getRequestDispatcher(Path.USER_ACCOUNT).forward(req, resp);
    }
}
