package com.my.services;

import com.my.db.constants.Currency;
import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.User;
import com.my.db.managers.Manager;
import com.my.db.managers.account.AccountManager;
import com.my.db.managers.account.AccountManagerImpl;
import com.my.util.EmailSender;
import com.my.util.Security;
import com.my.util.StackTrace;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AccountService extends AbstractService<Account> {

    private static final AccountManager ACCOUNT_MANAGER = AccountManagerImpl.getInstance();
    private static final AccountService instance = new AccountService();

    private AccountService() {
    }

    @Override
    protected Manager<Account> getManager() {
        return ACCOUNT_MANAGER;
    }

    public static Account get(long id) {
        return instance.abstractGet(id);
    }

    public static List<Account> search(String s) {
        return instance.abstractSearch(s);
    }

    public static List<Account> getAll() {
        return instance.abstractGetAll();
    }

    public static List<Account> getAll(Comparator<Account> comparator) {
        return instance.abstractGetAll(comparator);
    }

    public static boolean edit(Account account) {
        return instance.abstractUpdate(account);
    }

    public static boolean exists(long id) {
        return instance.abstractGet(id) != null;
    }

    public static boolean createAccountForUser(User user, Currency accountCurrency, String accountName) {
        Account account = new Account();
        account.setOwner(user.getId());
        account.setStatus(Status.ACTIVE);
        account.setCurrency(accountCurrency);
        account.setName(accountName);
        account.setPinCode(Security.generatePinCode());
        account.setBalance(BigDecimal.ZERO);
        account.setCvv(Security.generateCvv());
        account.setExpirationDate(Date.valueOf(LocalDate.now().plusYears(4L)));

        return instance.abstractInsert(account);
    }


    public static boolean userOwnsAccount(User user, Account account) {
        if (user == null || account == null) {
            return false;
        }

        return getUserAccounts(user.getId()).contains(account);
    }

    public static List<Account> getUserAccounts(long userId) {
        try {
            return ACCOUNT_MANAGER.getUserAccounts(userId);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return new ArrayList<>();
        }
    }

    public static List<Account> getUserAccounts(long userId, Comparator<Account> comparator) {
        return getUserAccounts(userId)
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public static boolean changeState(long id) {
        Account account;
        try {
            account = ACCOUNT_MANAGER.get(id);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }

        return changeState(account);
    }

    public static boolean changeState(Account account) {
        if (account.getStatus() == Status.ACTIVE) {
            return block(account);
        } else {
            return unblock(account);
        }
    }

    public static boolean block(Account account) {
        try {
            ACCOUNT_MANAGER.block(account);
            EmailSender.sendAccountBlockMessage(getOwner(account).getEmail(), account);
            return true;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }

    public static boolean unblock(Account account) {
        try {
            ACCOUNT_MANAGER.unblock(account);
            EmailSender.sendAccountUnblockMessage(getOwner(account).getEmail(), account);
            return true;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }

    public static User getOwner(Account account) {
        return UserService.get(account.getOwner());
    }
}

