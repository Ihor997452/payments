package com.my.web.controller.conmmand.admin;

import com.my.db.entity.Request;
import com.my.services.RequestService;
import com.my.util.StackTrace;
import com.my.web.controller.conmmand.Command;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractRequestCommand extends Command {
    protected Request getRequest(HttpServletRequest req) {
        Request request = null;
        try {
            long id = Long.parseLong(req.getParameter("request"));
            request = RequestService.get(id);
        } catch (NumberFormatException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
        }

        return request;
    }
}
