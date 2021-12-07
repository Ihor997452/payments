package com.my.web.controller.conmmand.admin;

import com.my.util.StackTrace;
import com.my.web.controller.conmmand.Command;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractChangeStateCommand extends Command {
    public long getId(HttpServletRequest req) {
        long id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            id = 0L;
        }

        return id;
    }
}
