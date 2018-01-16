import cucumber.api.java8.En;
import dtu.ws.fastmoney.*;
import core.utils.BankServerUtil;


import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CreateMerchantSteps implements En {
    public CreateMerchantSteps() {
        MerchantSimulator simulator = new MerchantSimulator();
        final String[] cvr = new String[1];
        final ResponseModel[] response = new ResponseModel[1];

        Given("^I have a merchant account in the FastMoney Bank with name \"(.+)\" \"(.+)\" and CVR \"(.+)\"$", (String firstname, String lastname, String CVR) -> {
            BankService bs = BankServerUtil.getServer();
            try {
                bs.retireAccount(bs.getAccountByCprNumber(CVR).getId());
            } catch (BankServiceException_Exception ex) {
                //Ignore
            }

            User user = new User();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setCprNumber(CVR);

            BigDecimal bd = new BigDecimal(200000);

            cvr[0] = bs.createAccountWithBalance(user, bd);

            assertNotNull(bs.getAccountByCprNumber(CVR));
        });

        When("^I ask DTU-Pay to create me a merchant account with name \"(.+)\" \"(.+)\" and CVR \"(.+)\"$", (String firstName, String lastName, String CVR) -> {
            simulator.clearDataStores();
            response[0] = simulator.createAccount(firstName, lastName, CVR);
        });

        Then("^The account was created successfully$", () -> {
            assertEquals(201, response[0].getStatus());
        });

        And("^I again ask DTU-Pay to create me a merchant account with name \"(.+)\" \"(.+)\" and CVR \"(.+)\"$", (String firstName, String lastName, String CVR) -> {
            response[0] = simulator.createAccount(firstName, lastName, CVR);
        });

        Then("^I receive a \"(.+)\" error from the system$", (String errorMsg) -> {
            assertEquals(errorMsg, response[0].getBody());
        });

        Given("^That I do not have a merchant account in the FastMoney Bank with CVR \"(.+)\"$", (String CVR) -> {
            // Write code here that turns the phrase above into concrete actions
            BankService bs = BankServerUtil.getServer();

            try {
                Account a = bs.getAccountByCprNumber(CVR);
                fail("BankServiceException_Exception should have been thrown");
            } catch (BankServiceException_Exception bse) {
                assertEquals("Account does not exist", bse.getMessage());
            }
        });

    }
}
