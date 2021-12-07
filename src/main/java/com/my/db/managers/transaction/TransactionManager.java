package com.my.db.managers.transaction;

import com.my.db.constants.Currency;
import com.my.db.entity.Account;
import com.my.db.entity.Transaction;
import com.my.db.managers.Manager;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public interface TransactionManager extends Manager<Transaction> {
    void doTransaction(Account from, BigDecimal amount, Account to, String message) throws SQLException;

    void doTopUp(BigDecimal amount, Account to, Currency currency) throws SQLException;

    List<Transaction> getUserTransactions(List<Account> accounts, Comparator<Transaction> comparator) throws SQLException;

    List<Transaction> getAccountTransactions(Account account) throws SQLException;
}

