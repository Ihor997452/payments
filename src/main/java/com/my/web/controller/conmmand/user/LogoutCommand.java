package com.my.web.controller.conmmand.user;

import com.my.web.controller.util.Links;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }

        resp.sendRedirect(Links.HOME);
    }
}
