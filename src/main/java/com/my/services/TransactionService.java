package com.my.services;


import com.my.db.constants.Currency;
import com.my.db.entity.Account;
import com.my.db.entity.Transaction;
import com.my.db.entity.User;
import com.my.db.managers.Manager;
import com.my.db.managers.transaction.TransactionManagerImpl;
import com.my.util.Comparators;
import com.my.util.StackTrace;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

public class TransactionService extends AbstractService<Transaction> {
    private static final TransactionManagerImpl TRANSACTION_MANAGER = TransactionManagerImpl.getInstance();
    private static final TransactionService instance = new TransactionService();

    private TransactionService() {
    }

    @Override
    protected Manager<Transaction> getManager() {
        return TRANSACTION_MANAGER;
    }

    public static Transaction get(long id) {
        return instance.abstractGet(id);
    }

    public static List<Transaction> getAll() {
        return instance.abstractGetAll();
    }

    public static List<Transaction> search(String s) {
        return instance.abstractSearch(s);
    }

    public static List<Transaction> getAccountTransactions(Account account) {
        try {
            return TRANSACTION_MANAGER.getAccountTransactions(account);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return new ArrayList<>();
        }
    }

    public static List<Transaction> getUserTransactions(User user) {
        try {
            return TRANSACTION_MANAGER.getUserTransactions(AccountService.getUserAccounts(user.getId()), Comparators.getTransactionComparator("id"));
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return new ArrayList<>();
        }
    }

    public static List<Transaction> getUserTransactions(User user, Comparator<Transaction> comparator) {
        try {
            return TRANSACTION_MANAGER.getUserTransactions(AccountService.getUserAccounts(user.getId()), comparator);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return new ArrayList<>();
        }
    }

    public static boolean userHasTransaction(User user, Transaction transaction) {
        if (user == null || transaction == null) {
            return false;
        }

        return getUserTransactions(user).contains(transaction);
    }

    public static boolean doTransaction(Account sender, BigDecimal amount, Account recipient, String message) {
        try {
            TRANSACTION_MANAGER.doTransaction(sender, amount, recipient, message);
            return true;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }

    public static boolean doTopUp(BigDecimal amount, Account recipient, Currency currency) {
        try {
            TRANSACTION_MANAGER.doTopUp(amount, recipient, currency);
            return true;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }
}

