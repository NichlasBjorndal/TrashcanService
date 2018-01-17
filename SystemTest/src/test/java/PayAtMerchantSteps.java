import core.utils.BankServerUtil;
import core.utils.GsonWrapper;
import cucumber.api.java8.En;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import io.swagger.model.Customer;
import io.swagger.model.Merchant;
import io.swagger.model.Transaction;


import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PayAtMerchantSteps implements En {
    ClientSimulator clientSimulator = new ClientSimulator();
    MerchantSimulator merchantSimulator = new MerchantSimulator();
    BankService server = BankServerUtil.getServer();
    String customerUUID;
    String merchantCVR;
    String customerCPR;
    String barcodeUUID;
    private int transferResponseCode;

    public PayAtMerchantSteps() {

        Given("^([^\"]*) ([^\"]*) with CPR number ([^\"]*) has an account in FastMoney Bank with balance ([^\"]*)$", (String firstName, String lastName, String cpr, String customerBalance) -> {
            customerCPR = cpr;
            User customer = new User();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setCprNumber(cpr);
            BigDecimal bD = new BigDecimal(customerBalance);
            server.createAccountWithBalance(customer, bD);
            assertEquals(cpr, server.getAccountByCprNumber(cpr).getUser().getCprNumber());

        });

        And("^([^\"]*) ([^\"]*) is customer in DTUPay with CPR number ([^\"]*)$", (String firstName, String lastName, String cpr) -> {
            Customer customer = new Customer();
            customer.setCpr(cpr);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            ResponseModel response = clientSimulator.createCustomer(customer);
            int responseCode = response.getStatus();
            customerUUID = (String) GsonWrapper.fromJson(response.getBody(), String.class);
            assertTrue(responseCode == 201);
        });

        And("^([^\"]*) ([^\"]*) with CVR number ([^\"]*) has an account in FastMoney Bank with balance ([^\"]*)$", (String firstName, String lastName, String cvr, String merchantBalance) -> {
            merchantCVR = cvr;
            User merchant = new User();
            merchant.setFirstName(firstName);
            merchant.setLastName(lastName);
            merchant.setCprNumber(cvr);
            BigDecimal bD = new BigDecimal(merchantBalance);
            server.createAccountWithBalance(merchant, bD);
            assertEquals(cvr, server.getAccountByCprNumber(cvr).getUser().getCprNumber());
        });

        And("^([^\"]*) ([^\"]*) is merchant in DTUPay with CVR number ([^\"]*)$", (String firstName, String lastName, String cvr) -> {
            Merchant merchant = new Merchant();
            merchant.setCvr(cvr);
            merchant.setFirstName(firstName);
            merchant.setLastName(lastName);
            ResponseModel response = merchantSimulator.createAccount(firstName, lastName, cvr);
            int responseCode = response.getStatus();
            assertEquals(201, responseCode);
        });

        And("^the customer requests and receives a barcode$", () -> {
            ResponseModel response = clientSimulator.requestBarcode(customerUUID);
            barcodeUUID = (String) GsonWrapper.fromJson(response.getBody(), String.class);
            assertEquals(200, response.getStatus());
            assertTrue(barcodeUUID.length() > 0);
        });

        When("^A merchant scans the customer's barcode and sends an invoice for a payment of ([^\"]*)$", (String amount) -> {
            BigDecimal paymentAmount = new BigDecimal(amount);
            Transaction transaction = new Transaction();
            transaction.setAmount(paymentAmount);
            transaction.setBarcode(barcodeUUID);
            transaction.setReceiverCVR(merchantCVR);
            ResponseModel response = merchantSimulator.payMerchant(transaction);
            transferResponseCode = response.getStatus();
        });

        Then("^then balance is ([^\"]*) on the customers account and the balance is ([^\"]*) on the merchant's account$", (String customerBalance, String merchantBalance) -> {
            assertEquals(customerBalance, server.getAccountByCprNumber(customerCPR).getBalance().toString());
            assertEquals(merchantBalance, server.getAccountByCprNumber(merchantCVR).getBalance().toString());
            server.retireAccount(server.getAccountByCprNumber(merchantCVR).getId());
            server.retireAccount(server.getAccountByCprNumber(customerCPR).getId());
            clientSimulator.clearDataStores();
        });
        Then("^the transfer is denied with response code ([^\"]*)$", (String responseCode) -> {
            assertEquals(Integer.parseInt(responseCode), transferResponseCode);
        });
        Then("^the transfer is accepted with response code ([^\"]*)$", (String responseCode) -> {
            assertEquals(Integer.parseInt(responseCode), transferResponseCode);
        });
        Given("^no accounts in FastMoney Bank and DTUPay exists with CPR ([^\"]*) and CVR ([^\"]*)$", (String cpr, String cvr) -> {
            clientSimulator.clearDataStores();
            try {
                server.retireAccount(server.getAccountByCprNumber(cpr).getId());
            } catch (BankServiceException_Exception e) {
                assertEquals("Account does not exist", e.getMessage());
            }
            try {
                server.retireAccount(server.getAccountByCprNumber(cvr).getId());
            } catch (BankServiceException_Exception e) {
                assertEquals("Account does not exist", e.getMessage());
            }
        });
    }
}
