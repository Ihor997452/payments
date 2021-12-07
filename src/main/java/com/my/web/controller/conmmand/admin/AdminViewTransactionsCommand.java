package com.my.web.controller.conmmand.admin;

import com.my.db.entity.Transaction;
import com.my.services.TransactionService;
import com.my.web.controller.util.Path;
import com.my.web.controller.conmmand.PaginationCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminViewTransactionsCommand extends PaginationCommand<Transaction> {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        List<Transaction> transactions;
        if (search != null && !search.trim().equals("")) {
            transactions = TransactionService.search(search.trim());
        } else {
            transactions = TransactionService.getAll();
        }

        transactions = setAttributes(req, transactions);
        req.setAttribute("transactions", transactions);
        req.getRequestDispatcher(Path.ADMIN_TRANSACTIONS).forward(req, resp);
    }
}
