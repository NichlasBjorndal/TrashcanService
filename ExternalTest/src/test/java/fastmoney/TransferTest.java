package fastmoney;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.junit.Assert.*;

import dtu.ws.fastmoney.BankServiceException_Exception;

import static org.junit.Assert.fail;

public class TransferTest {

    @ClassRule
    public static final TestResources res = new TestResources();

    @Before
    public void setUp() {
        res.before();
    }

    @Test
    public void transfer() throws BankServiceException_Exception {
        BigDecimal amount1 = new BigDecimal(1000000);
        BigDecimal amount2 = new BigDecimal(0);
        BigDecimal transferAmount = new BigDecimal(500000);
        BigDecimal expectedAmount = new BigDecimal(500000);

        String senderAccount = res.server.createAccountWithBalance(res.user1, amount1);
        String receiverAccount = res.server.createAccountWithBalance(res.user2, amount2);
        res.server.transferMoneyFromTo(senderAccount, receiverAccount, transferAmount,"Transfer from John to Jane");
        assertEquals(expectedAmount, res.server.getAccount(receiverAccount).getBalance());
        assertEquals(expectedAmount, res.server.getAccount(senderAccount).getBalance());
    }

    @Test
    public void transferNegative() throws BankServiceException_Exception {
        BigDecimal amount1 = new BigDecimal(1000000);
        BigDecimal amount2 = new BigDecimal(0);
        BigDecimal transferAmount = new BigDecimal(-500000);
        String senderAccount = res.server.createAccountWithBalance(res.user1, amount1);
        String receiverAccount = res.server.createAccountWithBalance(res.user2, amount2);

        try {
            res.server.transferMoneyFromTo(senderAccount, receiverAccount, transferAmount,"Transfer from John to Jane");
            fail("Expected that amount must be positive");
        } catch (BankServiceException_Exception e) {
            assertEquals("Amount must be positive", e.getMessage());
        }
    }

    @Test
    public void transferToSelf() throws BankServiceException_Exception {
        BigDecimal amount1 = new BigDecimal(1000000);
        BigDecimal transferAmount = new BigDecimal(500000);
        BigDecimal expectedAmount = new BigDecimal(1000000);

        String senderAccount = res.server.createAccountWithBalance(res.user1, amount1);
        res.server.transferMoneyFromTo(senderAccount, senderAccount, transferAmount,"Transfer from John to John");
        assertEquals(expectedAmount, res.server.getAccount(senderAccount).getBalance());
    }

    @Test
    public void transferZeroAccount() throws BankServiceException_Exception {
        BigDecimal amount1 = new BigDecimal(0);
        BigDecimal amount2 = new BigDecimal(500);
        BigDecimal transferAmount = new BigDecimal(50);
        BigDecimal expectedAmountReceiver = new BigDecimal(500);
        BigDecimal expectedAmountSender = new BigDecimal(0);

        String senderAccount = res.server.createAccountWithBalance(res.user1, amount1);
        String receiverAccount = res.server.createAccountWithBalance(res.user2, amount2);

        try {
            res.server.transferMoneyFromTo(senderAccount, receiverAccount, transferAmount,"Transfer from John to Jane");
            fail("Sender acount balance cannot be negative after transfer");
        } catch (BankServiceException_Exception e) {
            assertEquals("Debtor balance will be negative", e.getMessage());
        }
        assertEquals(expectedAmountReceiver, res.server.getAccount(receiverAccount).getBalance());
        assertEquals(expectedAmountSender, res.server.getAccount(senderAccount).getBalance());
    }

    @Test
    public void transferNoSender() throws BankServiceException_Exception {
        BigDecimal amount2 = new BigDecimal(0);
        BigDecimal transferAmount = new BigDecimal(500000);

        String senderAccount = "NoSender";
        String receiverAccount = res.server.createAccountWithBalance(res.user2, amount2);

        try {
            res.server.transferMoneyFromTo(senderAccount, receiverAccount, transferAmount,"Transfer from no one to Jane");
            fail("No sender account should exist");
        } catch (BankServiceException_Exception e) {
            assertEquals("Debtor account does not exist", e.getMessage());
        }
    }

    @Test
    public void transferNoReceiver() throws BankServiceException_Exception {
        BigDecimal amount1 = new BigDecimal(1000000);
        BigDecimal transferAmount = new BigDecimal(500000);

        String senderAccount = res.server.createAccountWithBalance(res.user1, amount1);
        String receiverAccount = "NoReceiver";

        try {
            res.server.transferMoneyFromTo(senderAccount, receiverAccount, transferAmount,"Transfer from John to No one");
            fail("No receiver account should exist");
        } catch (BankServiceException_Exception e) {
            assertEquals("Creditor account does not exist", e.getMessage());
        }
    }

    @After
    public void cleanUp(){
        res.after();
    }

}
