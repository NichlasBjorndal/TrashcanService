import Core.User.User;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class UserCucumberSteps implements En {

    public UserCucumberSteps() {
        
        Given("^Given I exist on the system$", () -> {

        });
        When("^I am logged in to the system$", () -> {

        });
        Then("^I can generate a barcode$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
