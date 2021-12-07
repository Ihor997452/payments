package com.my.web.controller.conmmand;

import com.my.web.controller.util.Path;
import com.my.web.controller.conmmand.admin.*;
import com.my.web.controller.conmmand.user.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private CommandContainer() {
    }

    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put(CommandNames.HOME, new HomeCommand());
        commands.put(CommandNames.REGISTER, new RegisterCommand());
        commands.put(CommandNames.VERIFY, new VerifyCommand());
        commands.put(CommandNames.LOGIN, new LoginCommand());
        commands.put(CommandNames.RESTORE, new RestoreCommand());
        commands.put(CommandNames.NEW_ACCOUNT, new NewAccountCommand());
        commands.put(CommandNames.TOP_UP, new TopUpCommand());
        commands.put(CommandNames.NEW_TRANSACTION, new NewTransactionCommand());
        commands.put(CommandNames.USER_ACCOUNT, new UserViewAccountCommand());
        commands.put(CommandNames.USER_TRANSACTIONS, new UserViewTransactionsCommand());
        commands.put(CommandNames.PDF_RECEIPT, new PEFReceiptCommand());
        commands.put(CommandNames.SEND_REQUEST, new SendRequestCommand());
        commands.put(CommandNames.LOGOUT, new LogoutCommand());
        commands.put(CommandNames.EDIT_ACCOUNT, new EditAccountCommand());
        commands.put(CommandNames.EDIT_PROFILE, new EditProfileCommand());
        commands.put(CommandNames.ADMIN_USERS, new AdminViewUsersCommand());
        commands.put(CommandNames.ADMIN_REQUESTS, new AdminViewRequestsCommand());
        commands.put(CommandNames.ADMIN_ACCOUNTS, new AdminViewAccountsCommand());
        commands.put(CommandNames.ADMIN_REQUEST_APPROVE, new ApproveRequestCommand());
        commands.put(CommandNames.ADMIN_REQUEST_DISAPPROVE, new DisapproveRequestCommand());
        commands.put(CommandNames.ADMIN_TRANSACTIONS, new AdminViewTransactionsCommand());
        commands.put(CommandNames.CHANGE_USER_STATE, new ChangeUserStateCommand());
        commands.put(CommandNames.CHANGE_ACCOUNT_STATE, new ChangeAccountStateCommand());
        commands.put(CommandNames.VERIFY_PAGE, new Command() {
            @Override
            public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.getRequestDispatcher(Path.VERIFY).forward(req, resp);
            }
        });
    }

    public static Command get(String name) {
        if (name == null || !commands.containsKey(name)) {
            return commands.get("home");
        }

        return commands.get(name);
    }
}
