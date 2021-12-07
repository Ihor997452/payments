package com.my.web.listener;

import com.my.db.constants.Currency;
import com.my.db.pool.Pool;
import com.my.util.StackTrace;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener {
    private static final Logger log = Logger.getLogger(Listener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Pool.getInstance().fillPool();
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            throw new IllegalStateException("Could not establish DB connection", e);
        }

        ServletContext context = sce.getServletContext();
        context.setAttribute("supportedCurrencies", Currency.values());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Pool.getInstance().shutdown();
        } catch (SQLException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            throw new IllegalStateException("Could not shutdown DB connections properly", e);
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("lang", "en");
    }
}

