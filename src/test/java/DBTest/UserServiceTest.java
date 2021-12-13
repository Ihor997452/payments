package DBTest;

import com.my.db.constants.Status;
import com.my.db.entity.User;
import com.my.db.managers.user.UserManagerImpl;
import com.my.services.UserService;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest extends AbstractServiceTest<User> {
    @BeforeAll
    public static void init() throws ClassNotFoundException, SQLException {
        connectionPoolInit();
        UserManagerImpl.getInstance().clear();
        databaseInit();
    }

    @Test
    @Order(1)
    void insertTest() {
        User user = createUser();
        Assertions.assertTrue(UserService.register(user));
    }

    @Test
    @Order(2)
    void getTest() {
        User user = createUser();
        user.setId(1);
        Assertions.assertEquals(UserService.get(1), user);
    }

    @Test
    @Order(3)
    void getByEmailTest() {
        User expectedUser = createUser();
        User actualUser = UserService.getByEmail(expectedUser.getEmail());

        if (actualUser == null) {
            Assertions.fail();
            return;
        }

        boolean equals = actualUser.getEmail().equals(expectedUser.getEmail());
        Assertions.assertTrue(equals);
    }

    @Test
    @Order(4)
    void editTest() {
        User user = createUser();
        String name = "edited";
        user.setName("edited");
        UserService.edit(user);

        Assertions.assertEquals(name, user.getName());
    }

    @Test
    @Order(5)
    void blockUserTest() {
        User user = UserService.get(1);
        UserService.block(user);
        Assertions.assertEquals(UserService.get(1).getStatusId(), Status.BLOCKED.getValue());
    }

    @Test
    @Order(6)
    void unblockUserTest() {
        User user = UserService.get(1);
        UserService.unblock(user);
        Assertions.assertEquals(UserService.get(1).getStatusId(), Status.ACTIVE.getValue());
    }

    @Test
    @Order(7)
    void loginIfCorrectDataTest() {
        Assertions.assertNotNull(UserService.logIn("test@test.test", "test"));
    }

    @Test
    @Order(8)
    void loginIfIncorrectDataTest() {
        Assertions.assertNull(UserService.logIn("test@test.test", "tes2t"));
    }
}
