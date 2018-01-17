package mdb.utils;

import core.user.Customer;
import core.utils.BankServerUtil;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import core.persistence.CustomerStore;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Gustav Madslund
 */
public class CustomerMessageHandlerTest {

    private Customer correctCustomer1, correctCustomer2, incorrectCustomer;
    BankService bs;
    User user1, user2;

    @Before
    public void setUp() throws Exception {
        bs = BankServerUtil.getServer();

        correctCustomer1 = new Customer("Oliver", "Clausen", "2605811623");
        user1 = new User();
        user1.setCprNumber(correctCustomer1.getCpr());
        user1.setFirstName(correctCustomer1.getFirstName());
        user1.setLastName(correctCustomer1.getLastName());
        BigDecimal bd = new BigDecimal(1);
        bs.createAccountWithBalance(user1, bd);

        correctCustomer2 = new Customer("Oliver", "Clausen", "2203822831");

        incorrectCustomer = new Customer("Mimir", "Mogensen", "2004682303");
        user2 = new User();
        user2.setCprNumber("2004682303");
        user2.setLastName("Mogensen");
        user2.setFirstName("Mimir");
        bs.createAccountWithBalance(user2, bd);


    }

    @After
    public void tearDown() throws Exception {
        bs.retireAccount(bs.getAccountByCprNumber(user1.getCprNumber()).getId());
        bs.retireAccount(bs.getAccountByCprNumber(user2.getCprNumber()).getId());
        CustomerStore.getInstance().clearStore();
    }

    @Test
    public void createValidCustomer() throws BankServiceException_Exception {
        String response = CustomerMessageHandler.createCustomer(correctCustomer1);

        assertNotNull(response);
        assertTrue(response.length() == 36);
        assertEquals(correctCustomer1.getUserID(), UUID.fromString(response));
    }

    @Test
    public void createCustomerAlreadyExists() {
        String response;
        CustomerMessageHandler.createCustomer(correctCustomer1);

        response = CustomerMessageHandler.createCustomer(correctCustomer1);
        assertEquals("Customer already exist in DTUPay", response);
    }

    @Test
    public void createCustomerNoBankAccount() {
        String response;

        response = CustomerMessageHandler.createCustomer(correctCustomer2);
        assertEquals("Customer doesn't have bank account", response);
    }

    @Test
    public void createCustomerInvalidFirstNameNull() {
        String response;

        incorrectCustomer.setFirstName(null);
        response = CustomerMessageHandler.createCustomer(incorrectCustomer);
        assertEquals("Invalid input", response);
    }

    @Test
    public void createCustomerInvalidFirstNameEmpty() {
        String response;

        incorrectCustomer.setFirstName("");
        response = CustomerMessageHandler.createCustomer(incorrectCustomer);
        assertEquals("Invalid input", response);
    }

    @Test
    public void createCustomerInvalidLastNameNull() {
        String response;

        incorrectCustomer.setLastName(null);
        response = CustomerMessageHandler.createCustomer(incorrectCustomer);
        assertEquals("Invalid input", response);
    }

    @Test
    public void createCustomerInvalidLastNameEmpty() {
        String response;

        incorrectCustomer.setLastName("");
        response = CustomerMessageHandler.createCustomer(incorrectCustomer);
        assertEquals("Invalid input", response);
    }

    @Test
    public void createCustomerInvalidCprTooShort() {
        String response;

        incorrectCustomer.setCpr("123456789");
        response = CustomerMessageHandler.createCustomer(incorrectCustomer);
        assertEquals("Invalid input", response);
    }

    @Test
    public void createCustomerInvalidCprTooLong() {
        String response;

        incorrectCustomer.setCpr("123456789");
        response = CustomerMessageHandler.createCustomer(incorrectCustomer);
        assertEquals("Invalid input", response);
    }

    @Test
    public void createCustomerInvalidCprContainsLetter() {
        String response;

        incorrectCustomer.setCpr("123456789a");
        response = CustomerMessageHandler.createCustomer(incorrectCustomer);
        assertEquals("Invalid input", response);
    }
}