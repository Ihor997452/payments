package com.my.web.controller.conmmand.admin;

import com.my.db.entity.Request;
import com.my.services.RequestService;
import com.my.web.controller.util.Path;
import com.my.web.controller.conmmand.PaginationCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminViewRequestsCommand extends PaginationCommand<Request> {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        List<Request> requests  ;
        if (search != null && !search.trim().equals("")) {
            requests = RequestService.search(search.trim());
        } else {
            requests = RequestService.getAll();
        }

        requests = setAttributes(req, requests);
        req.setAttribute("requests", requests);
        req.getRequestDispatcher(Path.ADMIN_REQUESTS).forward(req, resp);
    }
}
