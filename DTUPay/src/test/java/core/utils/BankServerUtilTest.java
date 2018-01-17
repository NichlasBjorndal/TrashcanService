package core.utils;

import core.FastMoneyTransaction;
import core.utils.BankServerUtil;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BankServerUtilTest {

    String userCPR1, userCPR2, userID1, userID2;

    @Before
    public void setUp() throws Exception {
        BankService bs = BankServerUtil.getServer();

        User user1 = new User();
        userCPR1 = "201047-0555";
        user1.setFirstName("Tosh");
        user1.setLastName("Møller");
        user1.setCprNumber(userCPR1);

        BigDecimal b1 = new BigDecimal(1000);

        userID1 = bs.createAccountWithBalance(user1, b1);

        User user2 = new User();
        userCPR2 = "061193-0976";
        user2.setFirstName("Caroline");
        user2.setLastName("Bach");
        user2.setCprNumber(userCPR2);

        BigDecimal b2 = new BigDecimal(1000);

        userID2 = bs.createAccountWithBalance(user2, b2);

    }

    @Test
    public void transferMoneyTest() throws Exception {
        FastMoneyTransaction transaction = new FastMoneyTransaction(userCPR1, userCPR2, new BigDecimal(501), "500 from Tosh to Caroline");

        BankServerUtil.transferMoney(transaction);

        BankService bs = BankServerUtil.getServer();
        assertEquals(new BigDecimal(499), bs.getAccount(userID1).getBalance());
        assertEquals(new BigDecimal(1501), bs.getAccount(userID2).getBalance());

    }

    @Test
    public void transferMoneyFailTest() throws Exception {
        FastMoneyTransaction transaction = new FastMoneyTransaction(userCPR1, userCPR2, new BigDecimal(501), "500 from Tosh to Caroline");

        BankServerUtil.transferMoney(transaction);

        try {
            BankServerUtil.transferMoney(transaction);
            fail("Should allow transaction when one user have too low balance");
        } catch (BankServiceException_Exception e) {
            assertEquals("Debtor balance will be negative", e.getMessage());
        }
        BankService bs = BankServerUtil.getServer();
        assertEquals(new BigDecimal(499), bs.getAccount(userID1).getBalance());
        assertEquals(new BigDecimal(1501), bs.getAccount(userID2).getBalance());
    }

    @After
    public void tearDown() throws Exception {
        BankService bs = BankServerUtil.getServer();
        try {
            bs.retireAccount(bs.getAccount(userID1).getId());
        } catch (BankServiceException_Exception e) {
            assertEquals("Account does not exist", e.getMessage());
        }

        try {
            bs.retireAccount(bs.getAccount(userID2).getId());
        } catch (BankServiceException_Exception e) {
            assertEquals("Account does not exist", e.getMessage());
        }

    }
}