package com.my.db.managers.transaction;

import com.my.db.constants.Currency;
import com.my.db.constants.SQLQueries;
import com.my.db.constants.TransactionStatus;
import com.my.db.entity.Transaction;
import com.my.db.managers.AbstractManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractTransactionManager extends AbstractManager<Transaction> implements TransactionManager {
    protected Transaction extractEntity(ResultSet set) throws SQLException {
        Transaction transaction = new Transaction();
        int i = 1;
        transaction.setId(set.getLong(i++));
        transaction.setFrom(set.getLong(i++));
        transaction.setTo(set.getLong(i++));
        transaction.setStatus(TransactionStatus.getStatus(set.getInt(i++)));
        transaction.setCurrency(Currency.getCurrency(set.getInt(i++)));
        transaction.setTime(set.getTimestamp(i++));
        transaction.setAmount(set.getBigDecimal(i++));
        transaction.setMessage(set.getString(i));
        return transaction;
    }

    protected String getInsertQuery() {
        return SQLQueries.INSERT_TRANSACTION;
    }

    protected String getUpdateQuery() {
        return SQLQueries.UPDATE_TRANSACTION;
    }

    protected String getDeleteQuery() {
        return SQLQueries.DELETE_TRANSACTION;
    }

    protected String getClearQuery() {
        return SQLQueries.CLEAR_TRANSACTIONS;
    }

    protected String getGetQuery() {
        return SQLQueries.GET_TRANSACTION;
    }

    protected String getGetAllQuery() {
        return SQLQueries.GET_ALL_TRANSACTIONS;
    }

    protected String getLastInsertIdQuery() {
        return SQLQueries.GET_LAST_TRANSACTION_ID;
    }

    protected String getSearchQuery() {
        return SQLQueries.SEARCH_TRANSACTION;
    }

    protected void prepareInsertStatement(PreparedStatement statement, Transaction entity) throws SQLException {
        this.prepareCommon(statement, entity);
    }

    protected void prepareUpdateStatement(PreparedStatement statement, Transaction entity) throws SQLException {
        this.prepareCommon(statement, entity);
        statement.setLong(8, entity.getId());
    }

    private void prepareCommon(PreparedStatement statement, Transaction entity) throws SQLException {
        int i = 1;
        statement.setLong(i++, entity.getFrom());
        statement.setLong(i++, entity.getTo());
        statement.setInt(i++, entity.getStatus().getValue());
        statement.setInt(i++, entity.getCurrency().getValue());
        statement.setTimestamp(i++, entity.getTime());
        statement.setBigDecimal(i++, entity.getAmount());
        statement.setString(i, entity.getMessage());
    }
}
