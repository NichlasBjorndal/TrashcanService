import cucumber.api.PendingException;
import cucumber.api.java8.En;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.User;
import io.swagger.model.Customer;
import mdb.utils.BankserverUtil;
import mdb.utils.GsonWrapper;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

public class ObtainBarcodeSteps implements En {
    public ObtainBarcodeSteps() {

        ClientSimulator cs = new ClientSimulator();
        final Customer[] customers = {null};
        final ResponseModel[] response = new ResponseModel[1];

        BankService bankService = BankserverUtil.GetServer();

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
            System.out.println(response[0].getStatus());
            assertTrue(response[0].getStatus() == 201);
        });
        When("^I request a new barcode$", () -> {
            String useruuid = (String) GsonWrapper.fromJson(response[0].getBody(), String.class);
            response[0] = cs.requestBarcode(useruuid);
        });
        Then("^I receive a new barcode$", () -> {
            String barcodeuuid = (String) GsonWrapper.fromJson(response[0].getBody(), String.class);

            //if it DOESN'T throw an exception it's a valid UUID
            UUID.fromString(barcodeuuid);

            assertTrue(response[0].getStatus() == 200);
        });
    }
}
