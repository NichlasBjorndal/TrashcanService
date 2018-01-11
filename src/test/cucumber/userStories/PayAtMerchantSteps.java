import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class PayAtMerchantSteps implements En {
    public PayAtMerchantSteps() {
        And("^I have received a barcode$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^A merchant attempts to verify my barcode for a purchase$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^DTUpay accepts the barcode and performs the transaction$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^My balance has changed$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^A merchant tries to withdraw too large a fund$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^DTUpay denies the transaction$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^My balance has not been changed$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^my balance has changed$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
