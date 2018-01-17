package core.utils;

import core.user.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias Linde
 */
public class GsonWrapperTest {

    private String expectedJsonFromString;
    private String actualString;
    private Customer actualCustomer;
    private String expectedJsonFromCustomer;


    @Before
    public void setUp() throws Exception {
        actualString = "test";
        expectedJsonFromString = "\"test\"";

        actualCustomer = new Customer("John", "Johnson", "1234567890");
        expectedJsonFromCustomer = "{\"cpr\":\"" + actualCustomer.getCpr() + "\",\"barcodes\":[],\"firstName\":\""
                + actualCustomer.getFirstName() + "\",\"lastName\":\"" + actualCustomer.getLastName() + "\",\"userID\":\""
                + actualCustomer.getUserID() + "\"}";
    }

    @Test
    public void toJsonTest() {
        String stringToJson = GsonWrapper.toJson(actualString);
        assertEquals(expectedJsonFromString, stringToJson);

        String json = GsonWrapper.toJson(actualCustomer);
        assertEquals(expectedJsonFromCustomer, json);

    }

    @Test
    public void fromJsonTest() {
        String stringFromJson = (String) GsonWrapper.fromJson(expectedJsonFromString, String.class);
        assertEquals(stringFromJson, actualString);

        Customer customerFromJson = (Customer) GsonWrapper.fromJson(expectedJsonFromCustomer, Customer.class);
        assertEquals(customerFromJson.getCpr(), actualCustomer.getCpr());
        assertEquals(customerFromJson.getBarcodes(), actualCustomer.getBarcodes());
        assertEquals(customerFromJson.getUserID(), actualCustomer.getUserID());
        assertEquals(customerFromJson.getFirstName(), actualCustomer.getFirstName());
        assertEquals(customerFromJson.getLastName(), actualCustomer.getLastName());
    }

    @Test
    public void toAndFromJsonTest() {
        String newString = (String) GsonWrapper.fromJson(GsonWrapper.toJson(actualString), String.class);
        assertEquals(actualString, newString);

        Customer newCustomer = (Customer) GsonWrapper.fromJson(GsonWrapper.toJson(actualCustomer), Customer.class);
        assertEquals(actualCustomer.getLastName(), newCustomer.getLastName());
        assertEquals(actualCustomer.getFirstName(), newCustomer.getFirstName());
        assertEquals(actualCustomer.getUserID(), newCustomer.getUserID());
        assertEquals(actualCustomer.getBarcodes(), newCustomer.getBarcodes());
        assertEquals(actualCustomer.getCpr(), newCustomer.getCpr());
    }
}