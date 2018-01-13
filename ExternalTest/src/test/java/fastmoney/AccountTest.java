package fastmoney;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

import dtu.ws.fastmoney.*;

import static org.junit.Assert.fail;

public class AccountTest {
    BankService server;
    User user1;
    User user2;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @ClassRule
    public static final TestResources res = new TestResources();

    @Before
    public void setUp() {
        res.before();
        this.server = res.server;
        this.user1 = res.user1;
        this.user2 = res.user2;
    }

    @Test
    public void noAccount(){
        try {
            server.getAccountByCprNumber(user1.getCprNumber());
            fail("Expected that account does not exist");
        } catch (BankServiceException_Exception e) {
            assertEquals("Account does not exist", e.getMessage());
        }
    }

    @Test
    public void createAccount() throws BankServiceException_Exception {
        BigDecimal amount = new BigDecimal(500);

        String accountID = server.createAccountWithBalance(user1, amount);
        Account account = server.getAccountByCprNumber(user1.getCprNumber());

        assertEquals(accountID, account.getId());
        assertEquals(user1.getCprNumber(),account.getUser().getCprNumber());
        assertEquals(user1.getFirstName(),account.getUser().getFirstName());
        assertEquals(user1.getLastName(),account.getUser().getLastName());

        server.retireAccount(account.getId());
        expectedException.expect(BankServiceException_Exception.class);
        server.getAccount(user1.getCprNumber());
    }

    @Test
    public void testBalance() throws BankServiceException_Exception {
        BigDecimal amount = new BigDecimal(1488);

        server.createAccountWithBalance(user2, amount);
        Account account = server.getAccountByCprNumber(user2.getCprNumber());

        assertEquals(amount, account.getBalance());

        server.retireAccount(account.getId());
        expectedException.expect(BankServiceException_Exception.class);
        server.getAccount(user2.getCprNumber());
    }

    @Test
    public void getAccountByCPR() throws BankServiceException_Exception {
        BigDecimal amount = new BigDecimal(1000);

        server.createAccountWithBalance(user2, amount);
        Account account = server.getAccountByCprNumber(user2.getCprNumber());

        assertEquals(user2.getCprNumber(),account.getUser().getCprNumber());
    }

    @Test
    public void getAccountByID() throws BankServiceException_Exception {
        BigDecimal amount = new BigDecimal(1000);

        String accountID = server.createAccountWithBalance(user2, amount);
        Account account = server.getAccount(accountID);

        assertEquals(user2.getCprNumber(),account.getUser().getCprNumber());
        assertEquals(accountID,account.getId());
        assertEquals(user2.getFirstName(),account.getUser().getFirstName());
        assertEquals(user2.getLastName(),account.getUser().getLastName());

    }

    @Test
    public void negativeBalance() throws BankServiceException_Exception {
        BigDecimal amount = new BigDecimal(-1000);

        try {
            server.createAccountWithBalance(user2, amount);
            fail("Expected 0 or positive balance only");
        } catch (BankServiceException_Exception e) {
            assertEquals("Initial balance must be 0 or positive", e.getMessage());
        }
    }

    @After
    public void cleanUp(){
       res.after();
    }
}
