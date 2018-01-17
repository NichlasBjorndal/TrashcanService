import cucumber.api.java8.En;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.User;
import io.swagger.model.Customer;
import core.utils.BankServerUtil;
import core.utils.GsonWrapper;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Gustav Madslund
 */
public class ObtainBarcodeSteps implements En {
    public ObtainBarcodeSteps() {

        ClientSimulator cs = new ClientSimulator();
        final Customer[] customers = {null};
        final ResponseModel[] response = new ResponseModel[2];

        BankService bankService = BankServerUtil.getServer();

        Given("^I have a DTUPay account$", () -> {
            cs.clearDataStores();

            customers[0] = new Customer();
            customers[0].setCpr("1234567890");
            customers[0].setFirstName("John");
            customers[0].setLastName("Doe");


            User user = new User();
            user.setFirstName(customers[0].getFirstName());
            user.setLastName(customers[0].getFirstName());
            user.setCprNumber(customers[0].getCpr());

            BigDecimal decimal = new BigDecimal(0);
            bankService.createAccountWithBalance(user, decimal);

            response[0] = cs.createCustomer(customers[0]);
            assertTrue(response[0].getStatus() == 201);
        });
        When("^I request a new barcode$", () -> {
            String useruuid = (String) GsonWrapper.fromJson(response[0].getBody(), String.class);
            response[0] = cs.requestBarcode(useruuid);
        });
        Then("^I receive a new barcode$", () -> {
            String barcodeuuid = (String) GsonWrapper.fromJson(response[0].getBody(), String.class);

            assertTrue(barcodeuuid.length() > 0);
            assertEquals(200,response[0].getStatus());

            retireBankAccount(bankService, customers[0].getCpr());
        });
        When("^I request a new barcode two times in a row$", () -> {
            String useruuid = (String) GsonWrapper.fromJson(response[0].getBody(), String.class);
            response[0] = cs.requestBarcode(useruuid);

            response[1] = cs.requestBarcode(useruuid);
        });
        Then("^I do not receive a barcode I already have$", () -> {
            String barcodeuuid1 = (String) GsonWrapper.fromJson(response[0].getBody(), String.class);
            String barcodeuuid2 = (String) GsonWrapper.fromJson(response[1].getBody(), String.class);

            assertTrue(!barcodeuuid1.equals(barcodeuuid2));
            retireBankAccount(bankService, customers[0].getCpr());
        });
        Given("^the customer does not exist$", () -> {
            cs.clearDataStores();

            customers[0] = new Customer();
            customers[0].setCpr("0987654321");
            customers[0].setFirstName("John");
            customers[0].setLastName("Doe");
        });
        When("^I try to request a new barcode$", () -> {
            response[0] = cs.requestBarcode(UUID.randomUUID().toString());
        });
        Then("^I receive a 'no customer found' error message$", () -> {
            int responseStatus = response[0].getStatus();
            assertEquals(403, responseStatus );
        });

    }

    public static void retireBankAccount(BankService bankService, String cpr) {
        try {
            Account accountByCprNumber = bankService.getAccountByCprNumber(cpr);
            if (accountByCprNumber != null) {
                String id = accountByCprNumber.getId();
                bankService.retireAccount(id);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}