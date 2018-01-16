package mdb.utils;

import core.barcode.BarcodeGeneratorInterface;
import core.persistence.CustomerStore;
import core.user.Customer;
import io.swagger.api.impl.BarcodeResponse;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @Author Nichlas Bjørndal
 */
public class BarcodeMessageHandlerTest {

    private String latestUID;

    @After
    public void tearDown() throws Exception {
        File file = new File(latestUID + ".png");
        if (file != null)
            file.delete();
    }

    @Test
    public void createBarcode_nullUuid() {
        //Arrange
        String uuid = null;
        String expectedResponse = BarcodeResponse.INVALID_INPUT.getValue();

        //Act
        String barcode = BarcodeMessageHandler.createBarcode(uuid);

        //Assert
        assertEquals(expectedResponse, barcode);
    }

    @Test
    public void createBarcode_invalidUuidNotNull() {
        //Arrange
        String uuid = "wpeofkw";
        String expectedResponse = BarcodeResponse.INVALID_INPUT.getValue();

        //Act
        String barcode = BarcodeMessageHandler.createBarcode(uuid);

        //Assert
        assertEquals(expectedResponse, barcode);
    }

    @Test
    public void createBarcode_uuidDoesntBelongToUser() {
        //Arrange
        String uuid = UUID.randomUUID().toString();
        String expectedResponse = BarcodeResponse.NO_USER.getValue();

        //Act
        String barcode = BarcodeMessageHandler.createBarcode(uuid);

        //Assert
        assertEquals(expectedResponse, barcode);
    }

    @Test
    public void createBarcode_uuidBelongsToUserAndIsValid() {
        //Arrange
        CustomerStore instance = CustomerStore.getInstance();
        Customer customer = new Customer();
        instance.saveCustomer(customer);

        String uuid = customer.getUserID().toString();


        //Act
        String barcode = BarcodeMessageHandler.createBarcode(uuid);
        latestUID = barcode;


        //Assert
        assertEquals(22, barcode.length());
    }


    @Test(expected = NullPointerException.class)
    public void createBarcode_IoExceptionThrown() throws IOException {
        //Arrange
        BarcodeGeneratorInterface barcodeGenerator = Mockito.mock(BarcodeGeneratorInterface.class);
        //Make the method throw an IOExpection
        when(barcodeGenerator.generateBarcode()).thenThrow(IOException.class);

        CustomerStore instance = CustomerStore.getInstance();
        Customer customer = new Customer();
        instance.saveCustomer(customer);

        String uuid = customer.getUserID().toString();

        //Act
        BarcodeMessageHandler.createBarcode(uuid, barcodeGenerator);

        //Assert
    }
}