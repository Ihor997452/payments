package DBTest;

import com.my.db.constants.Currency;
import com.my.db.entity.Account;
import com.my.db.entity.Transaction;
import com.my.db.entity.User;
import com.my.db.managers.account.AccountManagerImpl;
import com.my.db.managers.transaction.TransactionManagerImpl;
import com.my.db.managers.user.UserManagerImpl;
import com.my.services.AccountService;
import com.my.services.TransactionService;
import com.my.services.UserService;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionServiceTest extends AbstractServiceTest<Transaction> {
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
        //todo: write them test
        User user = createUser();
        UserService.register(user);
        AccountService.createAccountForUser(user, Currency.UAH, "name");
        AccountService.createAccountForUser(user, Currency.UAH, "name");

        BigDecimal transferAmount = BigDecimal.valueOf(5);
        Account sender = AccountService.get(1);
        sender.setBalance(BigDecimal.valueOf(20));
        BigDecimal senderBalance = sender.getBalance();
        Account receiver = AccountService.get(2);
        receiver.setBalance(BigDecimal.valueOf(20));
        BigDecimal receiverBalance = receiver.getBalance();

        boolean didTransaction = TransactionService.doTransaction(sender, transferAmount, receiver, "test");

        Assertions.assertAll(
                () -> Assertions.assertEquals(sender.getBalance(), senderBalance.subtract(transferAmount)),
                () -> Assertions.assertEquals(receiver.getBalance(), receiverBalance.add(transferAmount))
        );
    }

    @Override
    void getTest() {

    }

    @Override
    void editTest() {

    }
}
