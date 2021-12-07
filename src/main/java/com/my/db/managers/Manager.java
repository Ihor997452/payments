package com.my.db.managers;


import com.my.db.entity.Entity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Manager<T extends Entity> {
    void insert(T entity) throws SQLException;

    void insert(T entity, Connection connection) throws SQLException;

    void delete(T entity) throws SQLException;

    void delete(T entity, Connection connection) throws SQLException;

    void update(T entity) throws SQLException;

    void update(T entity, Connection connection) throws SQLException;

    void clear() throws SQLException;

    void clear(Connection connection) throws SQLException;

    T get(long id) throws SQLException;

    T get(long id, Connection connection) throws SQLException;

    List<T> getAll() throws SQLException;

    List<T> getAll(Connection connection) throws SQLException;

    List<T> search(String s) throws SQLException;
}

