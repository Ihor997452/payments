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
class TransactionServiceTest extends AbstractServiceTest<Transaction> {
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
        boolean senderBalanceLower = sender.getBalance().compareTo(senderBalance.subtract(transferAmount)) == 0;
        boolean receiverBalanceHigher = receiver.getBalance().compareTo(receiverBalance.add(transferAmount)) == 0;

        Assertions.assertTrue(didTransaction && senderBalanceLower && receiverBalanceHigher);
    }

    @Test
    @Order(2)
    void getTest() {
        Assertions.assertNotNull(TransactionService.get(1));
    }

    @Test
    @Order(3)
    void topUpTest() {
        Account account = AccountService.get(1);
        BigDecimal accountBalance = account.getBalance();
        TransactionService.doTopUp(BigDecimal.valueOf(20), account, account.getCurrency());
        Assertions.assertEquals(0, accountBalance.add(BigDecimal.valueOf(20)).compareTo(account.getBalance()));
    }

    @Test
    @Order(4)
    void userHasTransactionTest() {
        User user = UserService.get(1);
        Assertions.assertTrue(TransactionService.userHasTransaction(user, TransactionService.get(1)));
    }
}
