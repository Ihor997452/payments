package com.my.web.controller.conmmand.user;

import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.Transaction;
import com.my.db.entity.User;
import com.my.services.AccountService;
import com.my.services.TransactionService;
import com.my.util.Comparators;
import com.my.util.StackTrace;
import com.my.web.controller.util.Links;
import com.my.web.controller.util.Path;
import com.my.web.controller.conmmand.PaginationCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserViewTransactionsCommand extends PaginationCommand<Transaction> {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");

        Account account = null;
        if (req.getParameter("account") != null) {
            try {
                long id = Integer.parseInt(req.getParameter("account"));
                account = AccountService.get(id);
            } catch (NumberFormatException e) {
                log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            }
        }

        if (user.getStatusId() == Status.BLOCKED.getValue()) {
            resp.sendRedirect(Links.HOME);
            return;
        }

        if (!AccountService.userOwnsAccount(user, account)) {
            account = null;
        }

        String transactionOrder = req.getParameter("transaction-order");
        if (transactionOrder == null) {
            transactionOrder = "id";
        }

        List<Transaction> transactions;
        if (account != null) {
            transactions = TransactionService.getAccountTransactions(account);
        } else {
            transactions = TransactionService.getUserTransactions(user);
        }
        transactions.sort(Comparators.getTransactionComparator(transactionOrder));

        transactions = setAttributes(req, transactions);
        req.setAttribute("transactions", transactions);
        req.getRequestDispatcher(Path.USER_TRANSACTIONS).forward(req, resp);
    }
}
