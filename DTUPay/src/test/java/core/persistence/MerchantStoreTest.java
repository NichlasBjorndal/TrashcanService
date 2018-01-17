package core.persistence;

import core.user.Merchant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Mathias Linde
 */
public class MerchantStoreTest {

    private MerchantStore unitUnderTest;

    @Before
    public void setup(){
        unitUnderTest = MerchantStore.getInstance();
    }

    @After
    public void tearDown(){
        unitUnderTest.getMerchants().clear();
    }

    @Test
    public void getInstance() {
        //Arrange

        //Act
        MerchantStore instance = MerchantStore.getInstance();

        //Assert
        assertNotNull(instance);
        assertNotNull(instance.getMerchants());
        assertEquals(0, instance.getMerchants().size());
    }

    @Test
    public void saveMerchant() {
        //Arrange
        Merchant merchant = new Merchant();

        //Act
        unitUnderTest.saveMerchant(merchant);

        //Assert
        assertEquals(1, unitUnderTest.getMerchants().size());
    }

    @Test
    public void getMerchant() {
        //Arrange
        Merchant merchant = new Merchant();
        merchant.setCvr("1234");
        merchant.setFirstName("Super");
        merchant.setLastName("Brugsen");
        merchant.setAccountBalance(0.0);
        unitUnderTest.saveMerchant(merchant);

        //Act
        Merchant retrievedMerchant = unitUnderTest.getMerchant(merchant.getCvr());

        //Assert
        assertEquals(1, unitUnderTest.getMerchants().size());
        assertEquals(merchant, retrievedMerchant);
    }

    @Test
    public void clearStore() {
        //Arrange
        Merchant merchant = new Merchant();
        merchant.setCvr("1234");
        merchant.setFirstName("Super");
        merchant.setLastName("Brugsen");
        merchant.setAccountBalance(0.0);
        unitUnderTest.saveMerchant(merchant);

        //Act
        unitUnderTest.clearStore();

        //Assert
        assertEquals(0, unitUnderTest.getMerchants().size());
    }
}