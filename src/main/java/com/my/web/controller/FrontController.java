package com.my.web.controller;

import com.my.db.entity.User;
import com.my.services.AccountService;
import com.my.services.UserService;
import com.my.util.Comparators;
import com.my.web.controller.conmmand.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user!= null) {
            session.setAttribute("user", UserService.get(user.getId()));
            session.setAttribute("accounts", AccountService.getUserAccounts(user.getId(),
                    Comparators.getAccountComparator(req.getParameter("account-order"))));
        }

        String commandName = req.getParameter("command");
        CommandContainer.get(commandName).execute(req, resp);
    }
}
