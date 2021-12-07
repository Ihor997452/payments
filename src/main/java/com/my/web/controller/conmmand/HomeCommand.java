package com.my.web.controller.conmmand;

import com.my.db.constants.Roles;
import com.my.db.constants.Status;
import com.my.db.entity.User;
import com.my.web.controller.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomeCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            req.getRequestDispatcher(Path.HOME).forward(req, resp);
        } else {
            if (user.getRoleId() == Roles.USER.getValue() || user.getRoleId() == Roles.ADMIN.getValue()) {
                if (user.getStatusId() == Status.BLOCKED.getValue()) {
                    req.getRequestDispatcher(Path.BLOCKED).forward(req, resp);
                    return;
                }

                req.getRequestDispatcher(Path.USER_HOME).forward(req, resp);
            }
        }
    }
}
