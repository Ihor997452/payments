package com.my.db.managers.request;

import com.my.db.constants.*;
import com.my.db.entity.Request;
import com.my.db.managers.AbstractManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractRequestManager extends AbstractManager<Request> implements RequestManager {

    @Override
    protected Request extractEntity(ResultSet set) throws SQLException {
        Request request = new Request();
        int i = 1;
        request.setId(set.getLong(i++));
        request.setRequesterId(set.getLong(i++));
        request.setRequestStatus(RequestStatus.getStatus(set.getInt(i++)));
        request.setRequestType(RequestType.getType(set.getInt(i++)));
        request.setTime(set.getTimestamp(i));
        return request;
    }

    @Override
    protected String getInsertQuery() {
        return SQLQueries.INSERT_REQUEST;
    }

    @Override
    protected String getUpdateQuery() {
        return SQLQueries.UPDATE_REQUEST;
    }

    @Override
    protected String getDeleteQuery() {
        return SQLQueries.DELETE_REQUEST;
    }

    @Override
    protected String getClearQuery() {
        return SQLQueries.CLEAR_REQUESTS;
    }

    @Override
    protected String getGetQuery() {
        return SQLQueries.GET_REQUEST;
    }

    @Override
    protected String getGetAllQuery() {
        return SQLQueries.GET_ALL_REQUESTS;
    }

    @Override
    protected String getLastInsertIdQuery() {
        return SQLQueries.GET_LAST_REQUEST_ID;
    }

    @Override
    protected String getSearchQuery() {
        return SQLQueries.SEARCH_REQUEST;
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Request entity) throws SQLException {
        this.prepareCommon(statement, entity);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Request entity) throws SQLException {
        this.prepareCommon(statement, entity);
        statement.setLong(5, entity.getId());
    }

    private void prepareCommon(PreparedStatement statement, Request entity) throws SQLException {
        int i = 1;
        statement.setLong(i++, entity.getRequesterId());
        statement.setLong(i++, (long)entity.getRequestStatus().getValue());
        statement.setLong(i++, (long)entity.getRequestType().getValue());
        statement.setTimestamp(i, entity.getTime());
    }
}
