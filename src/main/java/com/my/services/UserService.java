package com.my.services;

import com.my.db.constants.Roles;
import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.User;
import com.my.db.managers.Manager;
import com.my.db.managers.user.UserManager;
import com.my.db.managers.user.UserManagerImpl;
import com.my.util.EmailSender;
import com.my.util.Security;
import com.my.util.StackTrace;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserService extends AbstractService<User> {
    private static final UserManager USER_MANAGER = UserManagerImpl.getInstance();
    private static final UserService instance = new UserService();

    private UserService() {
    }

    @Override
    protected Manager<User> getManager() {
        return USER_MANAGER;
    }

    public static List<User> getAll() {
        return instance.abstractGetAll();
    }

    public static List<User> search(String s) {
        return instance.abstractSearch(s);
    }

    public static boolean register(User user) {
        return instance.abstractInsert(user);
    }

    public static boolean edit(User user) {
        return instance.abstractUpdate(user);
    }

    public static User get(long id) {
        return instance.abstractGet(id);
    }

    public static boolean hasAccount(User user, Account account) {
        return AccountService.getUserAccounts(user.getId()).contains(account);
    }

    public static boolean changeState(long id) {
        User user;
        try {
            user = USER_MANAGER.get(id);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }

        return changeState(user);
    }

    public static boolean changeState(User user) {
        if (user.getRoleId() == Roles.ADMIN.getValue()) {
            return false;
        }

        if (user.getStatusId() == Status.ACTIVE.getValue()) {
            return block(user);
        } else {
            return unblock(user);
        }
    }

    public static boolean block(User user) {
        try {
            USER_MANAGER.block(user);
            EmailSender.sendProfileBlockMessage(user.getEmail());
            return true;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }

    public static boolean unblock(User user) {
        try {
            USER_MANAGER.unblock(user);
            EmailSender.sendProfileUnblockMessage(user.getEmail());
            return true;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }

    public static User getByEmail(String email) {
        try {
            return USER_MANAGER.getByEmail(email);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return null;
        }
    }

    public static List<User> getAllUsersExcludeAdmin() {
        List<User> users = instance.abstractGetAll();

        return users.stream()
                .filter(x -> x.getRoleId() == 1)
                .collect(Collectors.toList());
    }


    public static boolean existsByEmail(String email) {
        return getByEmail(email) != null;
    }

    public static User logIn(String email, String password) {
        User user = getByEmail(email);

        if (user == null) {
            return null;
        }

        if (Security.check(user.getPassword(), password, user.getSalt())) {
            return user;
        }

        return null;
    }

    public static boolean changePassword(String email, String newPassword) {
        User user = getByEmail(email);

        if (user == null) {
            return false;
        }

        return changePassword(user, newPassword);
    }

    public static boolean changePassword(User user, String newPassword) {
        newPassword = Security.encrypt(newPassword, Security.toByteArray(user.getSalt()));
        user.setPassword(newPassword);

        return instance.abstractUpdate(user);
    }

}
