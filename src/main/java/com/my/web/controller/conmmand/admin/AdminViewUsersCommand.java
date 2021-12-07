package com.my.web.controller.conmmand.admin;

import com.my.db.entity.User;
import com.my.services.UserService;
import com.my.web.controller.util.Path;
import com.my.web.controller.conmmand.PaginationCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminViewUsersCommand extends PaginationCommand<User> {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        List<User> users;
        if (search != null && !search.trim().equals("")) {
            users = UserService.search(search.trim());
        } else {
            users = UserService.getAllUsersExcludeAdmin();
        }

        users = setAttributes(req, users);
        req.setAttribute("users", users);
        req.getRequestDispatcher(Path.ADMIN_USERS).forward(req, resp);
    }
}
