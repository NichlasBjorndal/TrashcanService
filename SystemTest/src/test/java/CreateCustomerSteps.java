
import cucumber.api.java8.En;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.User;
import io.swagger.model.Customer;
import core.utils.BankServerUtil;
import core.utils.GsonWrapper;
import core.persistence.CustomerStore;

import java.math.BigDecimal;

import static org.junit.Assert.*;


/**
 * @author Simon Pileborg
 */
public class CreateCustomerSteps implements En {
    public CreateCustomerSteps() {
        ClientSimulator cs = new ClientSimulator();
        final Customer[] customers = {null};
        final ResponseModel[] response = new ResponseModel[1];

        BankService bankService = BankServerUtil.getServer();

        Given("^a customer with the name \"([^\"]*)\" and cpr number \"([^\"]*)\" without an account in FastMoney Bank$", (String name, String cpr) -> {

            CustomerStore.getInstance().clearStore();
            String[] names = name.split(" ");
            customers[0] = new Customer();
            customers[0].setCpr(cpr);
            customers[0].setFirstName(names[0]);
            customers[0].setLastName(names[1]);

            retireAccountByCpr(bankService, cpr);
        });

        When("^I ask the DTU-pay service to create me a Customer Account$", () -> {
            // Write code here that turns the phrase above into concrete actions
            response[0] = cs.createCustomer(customers[0]);
        });

        Then("^I receive a 'create customer' error from the system$", () -> {
            // Write code here that turns the phrase above into concrete actions
            int responseStatus = response[0].getStatus();
            assertTrue(responseStatus == 403);
        });

        Given("^I am a customer with name \"([^\"]*)\" and cpr number \"([^\"]*)\" with an account in FastMoney Bank$", (String name, String cpr) -> {

            cs.clearDataStores();
            customers[0] = new Customer();
            customers[0].setCpr(cpr);
            String[] names = name.split(" ");
            customers[0].setFirstName(names[0]);
            customers[0].setLastName(names[1]);

            retireAccountByCpr(bankService, cpr);

            User user = new User();
            user.setFirstName(name);
            user.setLastName(name);
            user.setCprNumber(cpr);

            BigDecimal decimal = new BigDecimal(0);
            bankService.createAccountWithBalance(user, decimal);


        });

        Then("the customer account is created with a UUID", () -> {
            int responseStatus = response[0].getStatus();
            assertTrue(responseStatus == 201);

            String responseUUID = (String) GsonWrapper.fromJson(response[0].getBody(), String.class);

            assertTrue(responseUUID.length() > 0);

            retireAccountByCpr(bankService, customers[0].getCpr());
        });

        Given("^I already have a DTUPay account with name \"([^\"]*)\" and cpr \"([^\"]*)\"$", (String name, String cpr) -> {
            // Write code here that turns the phrase above into concrete actions
            customers[0] = new Customer();
            customers[0].setCpr(cpr);
            String[] names = name.split(" ");
            customers[0].setFirstName(names[0]);
            customers[0].setLastName(names[1]);

            retireAccountByCpr(bankService, cpr);

            User user = new User();
            user.setFirstName(name);
            user.setLastName(name);
            user.setCprNumber(cpr);

            BigDecimal decimal = new BigDecimal(0);
            bankService.createAccountWithBalance(user, decimal);
            response[0] = cs.createCustomer(customers[0]);

        });

        Then("^I receive a 'account already exists' error from the system$", () -> {
            // Write code here that turns the phrase above into concrete actions
            int responseStatus = response[0].getStatus();
            assertTrue(responseStatus == 400);
            retireAccountByCpr(bankService, customers[0].getCpr());
        });

        Given("^I am a customer with invalid name \"([^\"]*)\" and cpr number \"([^\"]*)\" with an account in FastMoney Bank$", (String name, String cpr) -> {
            // Write code here that turns the phrase above into concrete actions
            customers[0] = new Customer();
            customers[0].setCpr(cpr);
            customers[0].setFirstName(name);
        });

        Then("^I receive a 'invalid input' error from the system$", () -> {
            // Write code here that turns the phrase above into concrete actions
            int responseStatus = response[0].getStatus();
            System.out.println(responseStatus);
            assertTrue(responseStatus == 405);
            retireAccountByCpr(bankService, customers[0].getCpr());
        });
        Given("^I am a customer with name \"([^\"]*)\" and invalid cpr number \"([^\"]*)\" with an account in FastMoney Bank$", (String name, String cpr) -> {
            customers[0] = new Customer();
            customers[0].setCpr(cpr);
            String[] names = name.split(" ");
            customers[0].setFirstName(names[0]);
            customers[0].setLastName(names[1]);
        });


    }

    private void retireAccountByCpr(BankService bankService, String cpr) {
        ObtainBarcodeSteps.retireBankAccount(bankService, cpr);
    }
}
