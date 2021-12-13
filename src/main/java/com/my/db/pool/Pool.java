package com.my.db.pool;

import com.my.util.PropertyReader;
import com.my.util.StackTrace;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class Pool {
    private static final Logger log = Logger.getLogger(Pool.class);
    private static Pool instance;
    private final int size = Integer.parseInt(PropertyReader.readPoolProperties("db.poolsize"));
    private final String url = PropertyReader.readPoolProperties("db.url");
    private final String user = PropertyReader.readPoolProperties("db.user");
    private final String password = PropertyReader.readPoolProperties("db.password");
    private final Map<Connection, Boolean> connectionPool = new HashMap<>();

    private Pool() {
    }

    public static synchronized Pool getInstance() {
        if (instance == null) {
            instance = new Pool();
        }

        return instance;
    }

    public void fillPool() throws SQLException {
        for(int i = 0; i < this.size; ++i) {
            this.connectionPool.put(DriverManager.getConnection(this.url, this.user, this.password), true);
        }
    }

    public synchronized Connection get() throws SQLException {
        Connection connection = null;
        for (Map.Entry<Connection, Boolean> entry : connectionPool.entrySet()) {
            if (entry.getValue()) {
                connection = entry.getKey();
                this.connectionPool.put(connection, false);
                break;
            }
        }

        if (connection == null) {
            try {
                Thread.sleep(100L);
                return this.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error(e.getMessage());
            }
        }

        if (connection != null && connection.isClosed()) {
            this.connectionPool.remove(connection);
            this.connectionPool.put(DriverManager.getConnection(this.url, this.user, this.password), true);
            return this.get();
        } else {
            return connection;
        }
    }

    public void free(Connection connection) {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }

            this.connectionPool.put(connection, true);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));

            try {
                this.shutdown();
                this.fillPool();
            } catch (SQLException e1) {
                log.error(e); log.error(StackTrace.getStackTrace(e));
                throw new IllegalStateException("Something is wrong with connection pool", e1);
            }
        }

    }

    public void shutdown() throws SQLException {
        for (Map.Entry<Connection, Boolean> entry : connectionPool.entrySet()) {
            entry.getKey().close();
        }

        this.connectionPool.clear();
    }
}