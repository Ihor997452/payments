package com.my.db.managers.transaction;

import com.my.db.constants.Currency;
import com.my.db.constants.SQLQueries;
import com.my.db.constants.Status;
import com.my.db.constants.TransactionStatus;
import com.my.db.entity.Account;
import com.my.db.entity.Transaction;
import com.my.db.managers.account.AccountManagerImpl;
import com.my.util.CurrencyConverter;
import com.my.util.StackTrace;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class TransactionManagerImpl extends AbstractTransactionManager {
    private static TransactionManagerImpl instance;

    private TransactionManagerImpl() {
    }

    public static synchronized TransactionManagerImpl getInstance() {
        if (instance == null) {
            instance = new TransactionManagerImpl();
        }

        return instance;
    }

    public void doTransaction(Account sender, BigDecimal amount, Account recipient, String message) throws SQLException {
        if (sender.getStatus() != Status.BLOCKED && recipient.getStatus() != Status.BLOCKED) {
            Connection connection = pool.get();
            Transaction transaction = new Transaction();

            try {
                transaction.setFrom(sender.getId());
                transaction.setTo(recipient.getId());
                transaction.setStatus(TransactionStatus.PENDING);
                transaction.setCurrency(sender.getCurrency());
                transaction.setTime(Timestamp.valueOf(LocalDateTime.now()));
                transaction.setAmount(amount);
                transaction.setMessage(message);
                this.insert(transaction, connection);

                sender.setBalance(sender.getBalance().subtract(amount));
                amount = CurrencyConverter.convert(transaction.getCurrency(), recipient.getCurrency(), amount);
                recipient.setBalance(recipient.getBalance().add(amount));

                connection.setAutoCommit(false);
                AccountManagerImpl.getInstance().update(sender, connection);
                AccountManagerImpl.getInstance().update(recipient, connection);
                transaction.setStatus(TransactionStatus.SUCCESSFUL);
                this.update(transaction, connection);
                connection.commit();
            } catch (SQLException e) {
                log.error(e); log.error(StackTrace.getStackTrace(e));
                connection.rollback();
                transaction.setStatus(TransactionStatus.FAILED);
                this.update(transaction);
                throw new SQLException("Could not create transaction", e);
            }
        }
    }

    public void doTopUp(BigDecimal amount, Account recipient, Currency currency) throws SQLException {
        if (recipient.getStatus() != Status.BLOCKED) {
            Connection connection = pool.get();
            Transaction transaction = new Transaction();

            try {
                transaction.setFrom(0L);
                transaction.setTo(recipient.getId());
                transaction.setStatus(TransactionStatus.PENDING);
                transaction.setCurrency(currency);
                transaction.setTime(Timestamp.valueOf(LocalDateTime.now()));
                transaction.setAmount(amount);
                transaction.setMessage("Top Up");
                this.insert(transaction, connection);

                connection.setAutoCommit(false);

                amount = CurrencyConverter.convert(currency, recipient.getCurrency(), amount);
                recipient.setBalance(recipient.getBalance().add(amount));

                AccountManagerImpl.getInstance().update(recipient, connection);
                transaction.setStatus(TransactionStatus.SUCCESSFUL);
                this.update(transaction, connection);
                connection.commit();
            } catch (SQLException e) {
                log.error(e); log.error(StackTrace.getStackTrace(e));
                connection.rollback();
                transaction.setStatus(TransactionStatus.FAILED);
                this.update(transaction);
                pool.free(connection);
                throw new SQLException("Could not top up", e);
            }
        }
    }

    public List<Transaction> getUserTransactions(List<Account> userAccounts, Comparator<Transaction> comparator) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        try {
            for (Account account : userAccounts) {
                transactions.addAll(this.getAccountTransactions(account));
            }

            return transactions;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            throw new SQLException(e);
        }
    }

    public List<Transaction> getAccountTransactions(Account account) throws SQLException {
        if (account.getStatus() == Status.BLOCKED) {
            return new ArrayList<>();
        } else {
            Connection connection = pool.get();
            List<Transaction> transactions = new ArrayList<>();
            String query = SQLQueries.GET_ACCOUNT_TRANSACTIONS;

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(account.getId()));
                statement.setString(2, String.valueOf(account.getId()));
                statement.execute();

                try (ResultSet set = statement.getResultSet()) {
                    while(set.next()) {
                        transactions.add(this.extractEntity(set));
                    }
                }
            } catch (SQLException e) {
                log.error(e); log.error(StackTrace.getStackTrace(e));
                throw new SQLException(e);
            } finally {
                pool.free(connection);
            }

            return transactions;
        }
    }
}


