package com.my.db.managers.user;

import com.my.db.entity.User;
import com.my.db.managers.Manager;
import java.sql.SQLException;
import java.util.List;

public interface UserManager extends Manager<User> {
    User getByEmail(String email) throws SQLException;

    void block(User user) throws SQLException;

    void unblock(User user) throws SQLException;

    List<User> search(String s) throws SQLException;
}
