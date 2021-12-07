package com.my.web.controller.filter;

import com.my.db.constants.Roles;
import com.my.db.constants.Status;
import com.my.db.entity.User;
import com.my.services.UserService;
import com.my.web.controller.conmmand.CommandNames;
import com.my.web.controller.util.Links;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandAccessFilter implements Filter {
    private static final Map<Roles, List<String>> accessMap = new HashMap<>();
    private static final List<String> adminCommands = new ArrayList<>();
    private static final List<String> userCommands = new ArrayList<>();
    private static final List<String> guestCommands = new ArrayList<>();
    private static final List<String> blockedCommands = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        blockedCommands.add(CommandNames.HOME);
        blockedCommands.add(CommandNames.SEND_REQUEST);

        guestCommands.add(CommandNames.HOME);
        guestCommands.add(CommandNames.REGISTER);
        guestCommands.add(CommandNames.RESTORE);
        guestCommands.add(CommandNames.LOGIN);
        guestCommands.add(CommandNames.VERIFY);
        guestCommands.add(CommandNames.VERIFY_PAGE);

        userCommands.add(CommandNames.HOME);
        userCommands.add(CommandNames.VERIFY);
        userCommands.add(CommandNames.VERIFY_PAGE);
        userCommands.add(CommandNames.EDIT_PROFILE);
        userCommands.add(CommandNames.SEND_REQUEST);
        userCommands.add(CommandNames.EDIT_ACCOUNT);
        userCommands.add(CommandNames.LOGOUT);
        userCommands.add(CommandNames.NEW_ACCOUNT);
        userCommands.add(CommandNames.NEW_TRANSACTION);
        userCommands.add(CommandNames.PDF_RECEIPT);
        userCommands.add(CommandNames.TOP_UP);
        userCommands.add(CommandNames.USER_ACCOUNT);
        userCommands.add(CommandNames.USER_TRANSACTIONS);

        adminCommands.add(CommandNames.ADMIN_USERS);
        adminCommands.add(CommandNames.ADMIN_ACCOUNTS);
        adminCommands.add(CommandNames.ADMIN_TRANSACTIONS);
        adminCommands.add(CommandNames.ADMIN_REQUESTS);
        adminCommands.add(CommandNames.ADMIN_REQUEST_APPROVE);
        adminCommands.add(CommandNames.ADMIN_REQUEST_DISAPPROVE);
        adminCommands.add(CommandNames.CHANGE_ACCOUNT_STATE);
        adminCommands.add(CommandNames.CHANGE_USER_STATE);
        adminCommands.addAll(userCommands);

        accessMap.put(Roles.USER, userCommands);
        accessMap.put(Roles.ADMIN, adminCommands);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        if (hasAccess(request, session)) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                session.setAttribute("user", UserService.get(user.getId()));
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher(Links.HOME).forward(servletRequest, servletResponse);
        }
    }

    private boolean hasAccess(HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String command = request.getParameter("command");

        if (command == null || command.isEmpty()) {
            return false;
        }

        if (user == null && guestCommands.contains(command)) {
            return true;
        }

        if (user == null) {
            return false;
        }

        if (user.getStatusId() == Status.BLOCKED.getValue() && blockedCommands.contains(command)) {
            return true;
        }

        Roles role = Roles.getRole(user.getRoleId());
        return accessMap.get(role).contains(command);
    }
}
