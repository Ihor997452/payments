package DBTest;

import com.my.db.constants.RequestStatus;
import com.my.db.constants.RequestType;
import com.my.db.entity.Request;
import com.my.db.managers.account.AccountManagerImpl;
import com.my.db.managers.transaction.TransactionManagerImpl;
import com.my.db.managers.user.UserManagerImpl;
import com.my.services.RequestService;
import com.my.services.UserService;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RequestServiceTest extends AbstractServiceTest<Request> {
    @BeforeAll
    static void init() throws SQLException, ClassNotFoundException {
        connectionPoolInit();
        TransactionManagerImpl.getInstance().clear();
        AccountManagerImpl.getInstance().clear();
        UserManagerImpl.getInstance().clear();
        databaseInit();
    }

    @Test
    @Order(1)
    void insertTest() {
        UserService.register(createUser());

        Request request = new Request();
        request.setTime(Timestamp.valueOf(LocalDateTime.now()));
        request.setRequestType(RequestType.UNBLOCK_USER);
        request.setRequestStatus(RequestStatus.PENDING);
        request.setRequesterId(1);
        Assertions.assertTrue(RequestService.send(request));
    }

    @Test
    @Order(2)
    void getTest() {
        Request request = RequestService.get(1);
        Assertions.assertAll(
                () -> Assertions.assertEquals(RequestType.UNBLOCK_USER, request.getRequestType()),
                () -> Assertions.assertEquals(1, request.getRequesterId()),
                () -> Assertions.assertEquals(RequestStatus.PENDING, request.getRequestStatus())
        );
    }

    @Test
    @Order(3)
    void approveTest() {
        Request request = RequestService.get(1);
        Assertions.assertTrue(RequestService.approve(request));
    }
}
