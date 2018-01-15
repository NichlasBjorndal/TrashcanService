import cucumber.api.PendingException;
import cucumber.api.java8.En;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateMerchantSteps implements En {
    public CreateMerchantSteps() {
        MerchantSimulator simulator = new MerchantSimulator();
        final String[] cvr = new String[1];
        final String[] response = new String[1];

        Given("^I have a merchant account in the FastMoney Bank with name \"(.+)\" \"(.+)\" and CVR \"(.+)\"$", (String firstname, String lastname, String CVR) -> {
            BankServiceService bss = new BankServiceService();
            BankService bs = bss.getBankServicePort();
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

            cvr[0] = bs.createAccountWithBalance(user,bd);

            assertNotNull(bs.getAccountByCprNumber(CVR));
        });

        When("^I ask DTU-Pay to create me a merchant account with name \"(.+)\" and CVR \"(.+)\"$", (String name, String CVR) -> {
            response[0] = simulator.createAccount(name,CVR);
        });

        Then("^An account is created with CVR \"(.+)\"$", (String CVR) -> {
            assertNotNull(response[0]);
            assertEquals(CVR,response[0]);

            BankServiceService bss = new BankServiceService();
            BankService bs = bss.getBankServicePort();

            bs.retireAccount(cvr[0]);
        });

        And("^I again ask DTU-Pay to create me a merchant account with name \"(.+)\" and CVR \"(.+)\"$", (String name, String CVR) -> {
            response[0] = simulator.createAccount(name, CVR);
        });

        Then("^I receive a 'create merchant' error from the system$", () -> {
            assertEquals("An account already exists",response[0]);
        });

        Given("^That the CVR-number is invalid$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        And("^the merchant account is not created$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Given("^That my merchant last or first name contains errors$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Given("^That I do not have a merchant account in the FastMoney Bank$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

    }
}
