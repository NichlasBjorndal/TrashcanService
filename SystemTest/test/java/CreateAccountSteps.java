import Core.User.User;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import simulator.ClientSimulator;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreateAccountSteps implements En {
    public CreateAccountSteps() {

        final ClientSimulator[] clientSimulator = new ClientSimulator[1];
        final String[] uuid = new String[1];
        final User[] user = new User[1];


        When("^I ask the DTU-pay service to create me an Account$", () -> {
            // Write code here that turns the phrase above into concrete actions
                clientSimulator[0] = new ClientSimulator();
                uuid[0] = clientSimulator[0].createAccount();

        });


        Then("^I receive a UUID$", () -> {
            // Write code here that turns the phrase above into concrete actions
            assertNotNull(uuid[0]);
            assertTrue(uuid[0].length() == 36);


        });
        And("^the account is created$", () -> {
            // Write code here that turns the phrase above into concrete actions
            user[0] = clientSimulator[0].getAccount(uuid[0]);
            assertNotNull(user[0]);
        });


        Given("^I already have an account$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^I receive an error from the system$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Given("^That my CPR-number is invalid$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^I recieve an error from the system$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^the account is not created$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Given("^That my last or first name contains errors$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
