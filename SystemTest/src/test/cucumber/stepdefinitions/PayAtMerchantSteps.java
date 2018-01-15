import core.barcode.BarcodeGenerator;
import core.user.Customer;
import core.user.Merchant;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import persistence.CustomerStore;
import persistence.MerchantStore;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PayAtMerchantSteps implements En {
    MerchantSimulator merchantSimulator = new MerchantSimulator();
    BankServiceService service = new BankServiceService();
    BankService server = service.getBankServicePort();
    String uuid;
    String cvr;

    public PayAtMerchantSteps() {
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
        And("^a merchant has an account$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^I have requested a barcode$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^I have received a barcode$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^A merchant attempts to verify my barcode for a purchase$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^customer have requested a barcode$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^customer have received a barcode$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^a barcode with uuid \"([^\"]*)\"$", (String arg0) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^the merchant attempts to verify this barcode for a purchase$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^a customer has an account$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^a customer then deletes his account$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^a merchant attempts to verify the customers barcode for a purchase$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^merchant does not have an account$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Given("^customer account with first name \"([^\"]*)\", last name \"([^\"]*)\" and CPR number \"([^\"]*)\" exists with account balance of  \"([^\"]*)\"$", (String firstName, String lastName, String cpr, String customerBalance) -> {
            Customer customer = new Customer(firstName, lastName, cpr);
            User cust = new User();
            cust.setFirstName(firstName);
            cust.setLastName(lastName);
            cust.setCprNumber(cpr);
            BigDecimal bD = new BigDecimal(customerBalance);
            server.createAccountWithBalance(cust,bD);
            assertEquals(cpr, server.getAccountByCprNumber(cpr).getUser().getCprNumber());
            server.retireAccount(server.getAccountByCprNumber(cpr).getId());
        });
        And("^merchant account with first name \"([^\"]*)\", last name \"([^\"]*)\" and CVR number \"([^\"]*)\" exists with account balance of \"([^\"]*)\"$", (String firstName, String lastName,  String cvr, String merchantBalance) -> {
            User merchant = new User();
            merchant.setFirstName(firstName);
            merchant.setLastName(lastName);
            merchant.setCprNumber(cvr);
            this.cvr = cvr;
            BigDecimal bD = new BigDecimal(merchantBalance);
            server.createAccountWithBalance(merchant,bD);
            assertEquals(cvr, server.getAccountByCprNumber(cvr).getUser().getCprNumber());
            server.retireAccount(server.getAccountByCprNumber(cvr).getId());
        });
        And("^the customer has received a barcode$", () -> {
            BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
            uuid = barcodeGenerator.generateBarcode().getUUID();
        });
        When("^A merchant scans the customer's barcode and sends an invoice for a payment of \"([^\"]*)\"$", (String amount) -> {





        });
    }
}
