package com.my.db.managers.user;

import com.my.db.constants.Roles;
import com.my.db.constants.SQLQueries;
import com.my.db.constants.Status;
import com.my.db.entity.User;
import com.my.db.managers.AbstractManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractUserManager extends AbstractManager<User> implements UserManager {
    @Override
    protected User extractEntity(ResultSet set) throws SQLException {
        User user = new User();
        int i = 1;
        user.setId(set.getLong(i++));
        user.setEmail(set.getString(i++));
        user.setName(set.getString(i++));
        user.setSurname(set.getString(i++));
        user.setPassword(set.getString(i++));
        user.setSalt(set.getString(i++));
        user.setRoleId(Roles.getRole(set.getInt(i++)));
        user.setStatusId(Status.getStatus(set.getInt(i)));
        return user;
    }

    @Override
    protected String getInsertQuery() {
        return SQLQueries.INSERT_USER;
    }

    @Override
    protected String getUpdateQuery() {
        return SQLQueries.UPDATE_USER;
    }

    @Override
    protected String getDeleteQuery() {
        return SQLQueries.DELETE_USER;
    }

    @Override
    protected String getClearQuery() {
        return SQLQueries.CLEAR_USERS;
    }

    @Override
    protected String getGetQuery() {
        return SQLQueries.GET_USER;
    }

    @Override
    protected String getGetAllQuery() {
        return SQLQueries.GET_ALL_USERS;
    }

    @Override
    protected String getLastInsertIdQuery() {
        return SQLQueries.GET_LAST_USER_INSERT_ID;
    }

    @Override
    protected String getSearchQuery() {
        return SQLQueries.SEARCH_USER;
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, User entity) throws SQLException {
        this.prepareCommon(statement, entity);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, User entity) throws SQLException {
        this.prepareCommon(statement, entity);
        statement.setLong(8, entity.getId());
    }

    private void prepareCommon(PreparedStatement statement, User entity) throws SQLException {
        int i = 1;
        statement.setString(i++, entity.getEmail());
        statement.setString(i++, entity.getName());
        statement.setString(i++, entity.getSurname());
        statement.setString(i++, entity.getPassword());
        statement.setString(i++, entity.getSalt());
        statement.setLong(i++, (long)entity.getRoleId());
        statement.setLong(i, (long)entity.getStatusId());
    }
}
