package DBTest;

import com.my.db.constants.Status;
import com.my.db.entity.Account;
import com.my.db.entity.User;
import com.my.db.managers.account.AccountManagerImpl;
import com.my.db.managers.user.UserManagerImpl;
import com.my.services.AccountService;
import com.my.services.UserService;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountServiceTest extends AbstractServiceTest<Account> {
    @BeforeAll
    static void init() throws ClassNotFoundException, SQLException {
        connectionPoolInit();
        UserManagerImpl.getInstance().clear();
        AccountManagerImpl.getInstance().clear();
        databaseInit();
    }

    @Test
    @Order(1)
    void insertTest() {
        User user = super.createUser();
        Account account = createAccount();
        UserService.register(user);
        Assertions.assertTrue(AccountService.createAccountForUser(user, account.getCurrency(), account.getName()));
    }

    @Test
    @Order(2)
    void getTest() {
        Account account = AccountService.get(1);
        boolean equals = account.getName().equals("test");
        Assertions.assertTrue(equals);
    }

    @Test
    @Order(3)
    void editTest() {
        Account account = AccountService.get(1);
        String name = "edited";
        account.setName("edited");
        AccountService.edit(account);
        Assertions.assertEquals(name, account.getName());
    }

    @Test
    @Order(4)
    void blockAccountTest() {
        Account account = AccountService.get(1);
        AccountService.block(account);
        Assertions.assertEquals(AccountService.get(1).getStatus(), Status.BLOCKED);
    }

    @Test
    @Order(5)
    void unblockAccountTest() {
        Account account = AccountService.get(1);
        AccountService.unblock(account);
        Assertions.assertEquals(AccountService.get(1).getStatus(), Status.ACTIVE);
    }

    @Test
    @Order(6)
    void userOwnsAccountTest() {
        User user = UserService.get(1);
        Account account = AccountService.get(1);
        Assertions.assertTrue(AccountService.userOwnsAccount(user, account));
    }

    @Test
    @Order(7)
    void getOwnerTest() {
        Account account = AccountService.get(1);
        User owner = AccountService.getOwner(account);
        Assertions.assertTrue(AccountService.userOwnsAccount(owner, account));
    }
}
