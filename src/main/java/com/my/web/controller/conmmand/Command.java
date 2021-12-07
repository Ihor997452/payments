package com.my.web.controller.conmmand;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Command {
    protected static final Logger log = Logger.getLogger(Command.class);

    public abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
