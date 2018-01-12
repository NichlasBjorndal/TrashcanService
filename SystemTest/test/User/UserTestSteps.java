package User;

import Core.Barcode.Model.Barcode;
import Core.User.User;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class UserTestSteps {
    private User testUser;

    @Given("^I exist on the system$")
    public void givenIExistOnTheSystem() throws Throwable {
        testUser = new User("Bob Bobson","12345678-1233");
    }

    @When("^I request a barcode$")
    public void iRequestABarcode() throws Throwable {
        testUser.requestBarcode();
    }

    @Then("^I can receive barcode$")
    public void iCanReceiveBarcode() throws Throwable {
        Barcode userBarcode = testUser.getBarcode();
        assertNotNull(userBarcode);
    }

    @Then("^I can fetch my name$")
    public void iCanFetchMyName() throws Throwable {
        String name = testUser.getName();
        assertEquals("Bob Bobson",name);
    }

    @And("^I can fetch my user ID$")
    public void iCanFetchMyUserID() throws Throwable {
        UUID uuid = testUser.getUserID();
        assertNotNull(uuid);
    }

    @And("^I can fetch my cpr number$")
    public void iCanFetchMyCprNumber() throws Throwable {
        String cpr = testUser.getCpr();
        assertEquals("12345678-1233",cpr);
    }
}
