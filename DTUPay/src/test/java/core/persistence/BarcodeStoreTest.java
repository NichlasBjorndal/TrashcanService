package core.persistence;

import core.barcode.BarcodeGenerator;
import core.barcode.Model.Barcode;
import core.user.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Mathias Linde
 */
public class BarcodeStoreTest {

    private BarcodeStore unitUnderTest;
    private BarcodeGenerator barcodeGenerator;

    @Before
    public void setup(){
        unitUnderTest = BarcodeStore.getInstance();
        barcodeGenerator = new BarcodeGenerator();
    }

    @After
    public void tearDown(){
        unitUnderTest.getBarcodes().clear();
    }

    @Test
    public void getInstance() {
        //Arrange

        //Act
        BarcodeStore instance = BarcodeStore.getInstance();

        //Assert
        assertNotNull(instance);
        assertNotNull(instance.getBarcodes());
        assertEquals(0, instance.getBarcodes().size());
    }

    @Test
    public void saveBarcode() {
        //Arrange
        Barcode barcode = null;
        try {
            barcode = barcodeGenerator.generateBarcode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Customer customer = new Customer();

        //Act
        unitUnderTest.saveBarcode(barcode, customer);

        //Assert
        assertEquals(1, unitUnderTest.getBarcodes().size());

        if(barcode.getFile() != null)
            barcode.getFile().delete();
    }

    @Test
    public void getCustomerId() {
        //Arrange
        Barcode barcode = null;
        try {
            barcode = barcodeGenerator.generateBarcode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Customer customer = new Customer();
        unitUnderTest.saveBarcode(barcode,customer);

        //Act
        UUID customerId = unitUnderTest.getCustomerId(barcode.getUUID().toString());

        //Assert
        assertEquals(1, unitUnderTest.getBarcodes().size());
        assertEquals(customer.getUserID(), customerId);

        if(barcode.getFile() != null)
            barcode.getFile().delete();
    }

    @Test
    public void clearStore() {
        //Arrange
        Barcode barcode = null;
        try {
            barcode = barcodeGenerator.generateBarcode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Customer customer = new Customer();
        unitUnderTest.saveBarcode(barcode, customer);

        //Act
        unitUnderTest.clearStore();

        //Assert
        assertEquals(0, unitUnderTest.getBarcodes().size());

        if(barcode.getFile() != null)
            barcode.getFile().delete();
    }
}