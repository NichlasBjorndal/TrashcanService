package core.persistence;

import core.user.Customer;
import cucumber.api.java.bs.A;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerStoreTest {

    private CustomerStore unitUnderTest;

    @Before
    public void setup(){
        unitUnderTest = CustomerStore.getInstance();
    }

    @After
    public void tearDown(){
        unitUnderTest.getCustomers().clear();;
    }


    @Test
    public void getInstance() {
        //Arrange

        //Act
        CustomerStore instance = CustomerStore.getInstance();

        //Assert
        assertNotNull(instance);
        assertNotNull(instance.getCustomers());
        assertEquals(0, instance.getCustomers().size());
    }

    @Test
    public void saveCustomer() {
        //Arrange
        Customer customer = new Customer();

        //Act
        unitUnderTest.saveCustomer(customer);

        //Assert
        assertEquals(1, unitUnderTest.getCustomers().size());
    }

    @Test
    public void getCustomer() {
        //Arrange
        Customer customer = new Customer();
        unitUnderTest.saveCustomer(customer);

        //Act
        Customer retrievedCustomer = unitUnderTest.getCustomer(customer.getUserID());

        //Assert
        assertEquals(1, unitUnderTest.getCustomers().size());
        assertEquals(customer, retrievedCustomer);
    }

    @Test
    public void clearStore() {
        //Arrange
        Customer customer = new Customer();
        unitUnderTest.saveCustomer(customer);

        //Act
        unitUnderTest.clearStore();

        //Assert
        assertEquals(0, unitUnderTest.getCustomers().size());
    }

    @Test
    public void cprExists() {
        //Arrange
        String cprNumber = "1234";

        Customer customer = new Customer();
        customer.setCpr(cprNumber);
        unitUnderTest.saveCustomer(customer);

        String cprNumber2 = "3234";

        Customer customer2 = new Customer();
        customer.setCpr(cprNumber2);
        unitUnderTest.saveCustomer(customer2);


        //Act
        boolean customerExists = unitUnderTest.cprExists(customer);

        //Assert
        assertTrue(customerExists);
    }
}