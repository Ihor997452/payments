package com.my.web.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession session = request.getSession();
        String lang = request.getParameter("lang");

        if (lang != null) {
            session.setAttribute("lang", lang);
        }

        if (session.getAttribute("lang") == null) {
            session.setAttribute("lang", request.getLocale().getLanguage());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
