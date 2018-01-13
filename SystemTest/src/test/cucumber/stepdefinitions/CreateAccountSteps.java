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


        Given("^That my last or first name contains errors$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Given("^I have a customer bank account in the FastMoney Bank$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^I ask the DTU-pay service to create me a Customer Account$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the customer account is created$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^the customer account is not created$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Given("^That I do not have a customer account in the FastMoney Bank$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
