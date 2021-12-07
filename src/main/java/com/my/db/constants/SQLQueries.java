package com.my.db.constants;

public class SQLQueries {
    public static final String CLEAR_USERS = "DELETE FROM users";
    public static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    public static final String GET_USER = "SELECT * FROM users WHERE id=?";
    public static final String GET_ALL_USERS = "SELECT * FROM users ORDER BY id DESC";
    public static final String SEARCH_USER = "SELECT * FROM users WHERE LOWER(CONCAT_WS(`id`, '█', `email`, '█', `name`, '█', `surname`)) LIKE ? AND `role_id` != 2 ORDER BY id DESC";
    public static final String GET_LAST_USER_INSERT_ID = "SELECT MAX(id) FROM users";
    public static final String INSERT_USER = "INSERT INTO users(email, name, surname, password, salt, role_id, status_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE users SET email=?, name=?, surname=?, password=?, salt=?, role_id=?, status_id=? WHERE id=?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    public static final String GET_USER_ACCOUNTS = "SELECT * FROM account WHERE owner=?";
    public static final String CLEAR_ACCOUNTS = "DELETE FROM account";
    public static final String DELETE_ACCOUNT = "DELETE FROM account WHERE id=?";
    public static final String GET_ACCOUNT = "SELECT * FROM account WHERE id=?";
    public static final String GET_ALL_ACCOUNTS = "SELECT * FROM account ORDER BY id DESC";
    public static final String SEARCH_ACCOUNT = "SELECT * FROM account WHERE LOWER(CONCAT_WS(`owner`, '█', `id`, '█', `name`)) LIKE ? ORDER BY id DESC";
    public static final String GET_LAST_ACCOUNT_ID = "SELECT MAX(id) FROM account";
    public static final String INSERT_ACCOUNT = "INSERT INTO account(owner, status, currency, name, pin_code, balance, cvv, expiration_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ACCOUNT = "UPDATE account SET owner=?, status=?, currency=?, name=?, pin_code=?, balance=?, cvv=?, expiration_date=? WHERE id=?";
    public static final String CLEAR_TRANSACTIONS = "DELETE FROM transaction";
    public static final String DELETE_TRANSACTION = "DELETE FROM transaction WHERE id=?";
    public static final String GET_TRANSACTION = "SELECT * FROM transaction WHERE id=?";
    public static final String GET_ALL_TRANSACTIONS = "SELECT * FROM transaction ORDER BY id DESC";
    public static final String SEARCH_TRANSACTION = "SELECT * FROM transaction WHERE LOWER(CONCAT_WS(`id`, '█', `from`, '█', `to`)) LIKE ? ORDER BY id DESC";
    public static final String GET_LAST_TRANSACTION_ID = "SELECT MAX(id) FROM transaction";
    public static final String INSERT_TRANSACTION = "INSERT INTO transaction(`from`, `to`, `status`, `sender_currency`, `time`, `amount`, `message`) VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_TRANSACTION = "UPDATE transaction SET `from`=?, `to`=?, status=?, `sender_currency`=?, time=?, amount=?, message=? WHERE id=?";
    public static final String GET_ACCOUNT_TRANSACTIONS = "SELECT * FROM transaction WHERE `from` LIKE ? OR `to` LIKE ? ORDER BY id DESC";
    public static final String CLEAR_REQUESTS = "DELETE FROM request";
    public static final String DELETE_REQUEST = "DELETE FROM request WHERE id=?";
    public static final String GET_REQUEST = "SELECT * FROM request WHERE id=?";
    public static final String GET_ALL_REQUESTS = "SELECT * FROM request WHERE request_status=2 ORDER BY id DESC";
    public static final String SEARCH_REQUEST = "SELECT * FROM request WHERE LOWER(CONCAT_WS(`id`, '█', `requester_id`)) LIKE ? AND request_status=2 ORDER BY id DESC";
    public static final String GET_LAST_REQUEST_ID = "SELECT MAX(id) FROM request";
    public static final String INSERT_REQUEST = "INSERT INTO request(requester_id, request_status, request_type, time) VALUES(?, ?, ?, ?)";
    public static final String UPDATE_REQUEST = "UPDATE request SET requester_id=?, request_status=?, request_type=?, time=? WHERE id=?";

    private SQLQueries() {
    }
}
