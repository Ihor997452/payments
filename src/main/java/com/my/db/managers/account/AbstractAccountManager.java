package com.my.db.managers.account;

import com.my.db.constants.Currency;
import com.my.db.constants.SQLQueries;
import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.managers.AbstractManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractAccountManager extends AbstractManager<Account> implements AccountManager {

    @Override
    protected Account extractEntity(ResultSet set) throws SQLException {
        Account account = new Account();
        int i = 1;
        account.setId(set.getLong(i++));
        account.setOwner(set.getLong(i++));
        account.setStatus(Status.getStatus(set.getInt(i++)));
        account.setCurrency(Currency.getCurrency(set.getInt(i++)));
        account.setName(set.getString(i++));
        account.setPinCode(set.getInt(i++));
        account.setBalance(set.getBigDecimal(i++));
        account.setCvv(set.getInt(i++));
        account.setExpirationDate(set.getDate(i));
        return account;
    }

    @Override
    protected String getInsertQuery() {
        return SQLQueries.INSERT_ACCOUNT;
    }

    @Override
    protected String getUpdateQuery() {
        return SQLQueries.UPDATE_ACCOUNT;
    }

    @Override
    protected String getDeleteQuery() {
        return SQLQueries.DELETE_ACCOUNT;
    }

    @Override
    protected String getClearQuery() {
        return SQLQueries.CLEAR_ACCOUNTS;
    }

    @Override
    protected String getGetQuery() {
        return SQLQueries.GET_ACCOUNT;
    }

    @Override
    protected String getGetAllQuery() {
        return SQLQueries.GET_ALL_ACCOUNTS;
    }

    @Override
    protected String getLastInsertIdQuery() {
        return SQLQueries.GET_LAST_ACCOUNT_ID;
    }

    @Override
    protected String getSearchQuery() {
        return SQLQueries.SEARCH_ACCOUNT;
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Account entity) throws SQLException {
        this.prepareCommon(statement, entity);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Account entity) throws SQLException {
        this.prepareCommon(statement, entity);
        statement.setLong(9, entity.getId());
    }

    private void prepareCommon(PreparedStatement statement, Account entity) throws SQLException {
        int i = 1;
        statement.setLong(i++, entity.getOwner());
        statement.setLong(i++, entity.getStatus().getValue());
        statement.setLong(i++, entity.getCurrency().getValue());
        statement.setString(i++, entity.getName());
        statement.setInt(i++, entity.getPinCode());
        statement.setBigDecimal(i++, entity.getBalance());
        statement.setInt(i++, entity.getCvv());
        statement.setDate(i, entity.getExpirationDate());
    }
}

