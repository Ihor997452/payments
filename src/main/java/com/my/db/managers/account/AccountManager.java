package com.my.db.managers.account;

import com.my.db.entity.Account;
import com.my.db.managers.Manager;
import java.sql.SQLException;
import java.util.List;

public interface AccountManager extends Manager<Account> {
    List<Account> getUserAccounts(long id) throws SQLException;

    void block(Account account) throws SQLException;

    void unblock(Account account) throws SQLException;
}
