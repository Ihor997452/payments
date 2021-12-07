package com.my.web.controller.conmmand.admin;

import com.my.db.entity.Account;
import com.my.services.AccountService;
import com.my.web.controller.util.Path;
import com.my.web.controller.conmmand.PaginationCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminViewAccountsCommand extends PaginationCommand<Account> {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        List<Account> accounts;
        if (search != null && !search.trim().equals("")) {
            accounts = AccountService.search(search.trim());
        } else {
            accounts = AccountService.getAll();
        }

        accounts = setAttributes(req, accounts);
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher(Path.ADMIN_ACCOUNTS).forward(req, resp);
    }
}
