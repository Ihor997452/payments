package com.my.db.managers.account;

import com.my.db.constants.SQLQueries;
import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.util.StackTrace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountManagerImpl extends AbstractAccountManager {
    private static AccountManagerImpl instance;

    private AccountManagerImpl() {
    }

    public static synchronized AccountManagerImpl getInstance() {
        if (instance == null) {
            instance = new AccountManagerImpl();
        }

        return instance;
    }

    @Override
    public List<Account> getUserAccounts(long userId) throws SQLException {
        List<Account> accounts = new ArrayList();
        Connection connection = pool.get();
        String query = SQLQueries.GET_USER_ACCOUNTS;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.execute();

            try (ResultSet set = statement.getResultSet()) {
                while(set.next()) {
                    accounts.add(this.extractEntity(set));
                }
            }
        } catch (SQLException var21) {
            log.error(var21);
            log.error(StackTrace.getStackTrace(var21));
            throw new SQLException("Could not abstractGet all accounts", var21);
        } finally {
            pool.free(connection);
        }

        return accounts;
    }

    @Override
    public void block(Account account) throws SQLException {
        account.setStatus(Status.BLOCKED);
        this.update(account);
    }

    @Override
    public void unblock(Account account) throws SQLException {
        account.setStatus(Status.ACTIVE);
        this.update(account);
    }
}

