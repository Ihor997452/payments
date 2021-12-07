package com.my.db.managers;

import com.my.db.entity.Entity;
import com.my.db.pool.Pool;
import com.my.util.StackTrace;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class AbstractManager<T extends Entity> implements Manager<T> {
    protected static final Logger log = Logger.getLogger(AbstractManager.class);
    protected static final Pool pool = Pool.getInstance();

    protected abstract T extractEntity(ResultSet set) throws SQLException;

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getClearQuery();

    protected abstract String getGetQuery();

    protected abstract String getGetAllQuery();

    protected abstract String getLastInsertIdQuery();

    protected abstract String getSearchQuery();

    protected abstract void prepareInsertStatement(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void prepareUpdateStatement(PreparedStatement statement, T entity) throws SQLException;

    public void insert(T entity) throws SQLException {
        Connection connection = pool.get();

        try {
            this.insert(entity, connection);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            throw new SQLException("Could not insert record", e);
        } finally {
            pool.free(connection);
        }
    }

    public void insert(T entity, Connection connection) throws SQLException {
        String query = this.getInsertQuery();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            prepareInsertStatement(statement, entity);
            statement.execute();
            entity.setId(getLastInsertId());
        }
    }

    public void update(T entity) throws SQLException {
        Connection connection = pool.get();

        try {
            this.update(entity, connection);
        } catch (SQLException e) {
            log.error(e);
            log.error(StackTrace.getStackTrace(e));
            throw new SQLException("Could not update record", e);
        } finally {
            pool.free(connection);
        }
    }

    public void update(T entity, Connection connection) throws SQLException {
        String query = this.getUpdateQuery();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            this.prepareUpdateStatement(statement, entity);
            statement.execute();
        }
    }

    public void delete(T entity) throws SQLException {
        Connection connection = pool.get();

        try {
            this.delete(entity, connection);
        } catch (SQLException e) {
            log.error(e);
            log.error(StackTrace.getStackTrace(e));
            throw new SQLException("Could not delete record", e);
        } finally {
            pool.free(connection);
        }
    }

    public void delete(T entity, Connection connection) throws SQLException {
        String query = this.getDeleteQuery();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, entity.getId());
            statement.execute();
        }
    }

    public void clear() throws SQLException {
        Connection connection = pool.get();

        try {
            this.clear(connection);
        } catch (SQLException e) {
            log.error(e);
            log.error(StackTrace.getStackTrace(e));
            throw new SQLException("Could not clear DataBase", e);
        } finally {
            pool.free(connection);
        }

    }

    public void clear(Connection connection) throws SQLException {
        String query = this.getClearQuery();
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    public T get(long id) throws SQLException {
        Connection connection = pool.get();

        T entity;
        try {
            entity = this.get(id, connection);
        } catch (SQLException e) {
            log.error(e);
            log.error(StackTrace.getStackTrace(e));
            throw new SQLException("Could not abstractGet the record", e);
        } finally {
            pool.free(connection);
        }

        return entity;
    }

    public T get(long id, Connection connection) throws SQLException {
        String query = this.getGetQuery();
        T entity;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();

            try (ResultSet set = statement.getResultSet()) {
                if (!set.next()) {
                    throw new SQLException("Could not abstractGet the record");
                }

                entity = this.extractEntity(set);
            }
        }

        return entity;
    }

    public List<T> getAll() throws SQLException {
        Connection connection = pool.get();

        List<T> list;
        try {
            list = this.getAll(connection);
        } catch (SQLException e) {
            log.error(e);
            log.error(StackTrace.getStackTrace(e));
            throw new SQLException("Could not abstractGet all records", e);
        } finally {
            pool.free(connection);
        }

        return list;
    }

    public List<T> getAll(Connection connection) throws SQLException {
        List<T> list = new ArrayList<>();
        String query = this.getGetAllQuery();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet set = statement.executeQuery(query)) {
                while(set.next()) {
                    list.add(this.extractEntity(set));
                }
            }
        }

        return list;
    }

    private long getLastInsertId() throws SQLException {
        Connection connection = pool.get();
        String query = this.getLastInsertIdQuery();

        long id;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet set = statement.executeQuery(query)) {
                set.next();
                id = set.getLong(1);
            }
        } catch (SQLException e) {
            log.error(e);
            log.error(StackTrace.getStackTrace(e));
            throw new SQLException(e);
        } finally {
            pool.free(connection);
        }

        return id;
    }

    public List<T> search(String find) throws SQLException {
        List<T> entities = new ArrayList<>();
        find = "%" + find + "%";
        Connection connection = pool.get();
        String query = this.getSearchQuery();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, find);
            statement.execute();

            try (ResultSet set = statement.getResultSet()) {
                while(set.next()) {
                    entities.add(this.extractEntity(set));
                }
            }
        } catch (SQLException e) {
            log.error(e);
            log.error(StackTrace.getStackTrace(e));
            throw new SQLException("Could not abstractGet all matches", e);
        } finally {
            pool.free(connection);
        }

        return entities;
    }
}
