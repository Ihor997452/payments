package com.my.db.managers.user;

import com.my.db.constants.SQLQueries;
import com.my.db.constants.Status;
import com.my.db.entity.User;
import com.my.util.StackTrace;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManagerImpl extends AbstractUserManager {
    private static UserManagerImpl instance;

    private UserManagerImpl() {
    }

    public static synchronized UserManagerImpl getInstance() {
        if (instance == null) {
            instance = new UserManagerImpl();
        }

        return instance;
    }

    @Override
    public User getByEmail(String email) throws SQLException {
        Connection connection = pool.get();
        String query = SQLQueries.GET_USER_BY_EMAIL;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.execute();

            try (ResultSet set = statement.getResultSet()) {
                if (set.next()) {
                    return this.extractEntity(set);
                }

                return null;
            }
        } catch (SQLException e) {
            log.error(e);
            log.error(StackTrace.getStackTrace(e));
            throw new SQLException("Cannot abstractGet user by email", e);
        } finally {
            pool.free(connection);
        }
    }

    public void block(long id) throws SQLException {
        User user = this.get(id);
        this.block(user);
    }

    public void unblock(long id) throws SQLException {
        User user = this.get(id);
        this.unblock(user);
    }

    public void block(User user) throws SQLException {
        user.setStatusId(Status.BLOCKED);
        this.update(user);
    }

    public void unblock(User user) throws SQLException {
        user.setStatusId(Status.ACTIVE);
        this.update(user);
    }
}
