package DBTest;

import com.my.db.constants.Currency;
import com.my.db.constants.Roles;
import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.Entity;
import com.my.db.entity.User;
import com.my.db.pool.Pool;
import com.my.util.PropertyReader;
import com.my.util.Security;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

abstract class AbstractServiceTest<T extends Entity> {
    @Test
    abstract void insertTest();

    @Test
    abstract void getTest();

    @Test
    abstract void editTest();

    protected static void connectionPoolInit() throws ClassNotFoundException, SQLException {
        String driver = PropertyReader.readPoolProperties("db.driver");
        Class.forName(driver);
        Pool.getInstance().fillPool();
    }

    protected static void databaseInit() throws SQLException {
        Connection connection = Pool.getInstance().get();
        try (Statement statement = connection.createStatement()) {
            statement.execute("alter table `users` AUTO_INCREMENT=1");
            statement.execute("alter table `account` AUTO_INCREMENT=1");
            statement.execute("alter table `transaction` AUTO_INCREMENT=1");
            statement.execute("alter table `request` AUTO_INCREMENT=1");
        }
    }

    protected User createUser() {
        User user = new User();
        user.setName("test");
        user.setSurname("test");
        user.setEmail("test@test.test");
        user.setStatusId(Status.ACTIVE);
        user.setRoleId(Roles.USER);

        byte[] salt = Security.getSalt();
        user.setPassword(Security.encrypt("test", salt));
        user.setSalt(Security.toHexString(salt));

        return user;
    }

    protected Account createAccount() {
        Account account = new Account();
        account.setName("test");
        account.setCurrency(Currency.UAH);
        account.setBalance(BigDecimal.valueOf(20));

        return account;
    }
}
