package mdb.utils;

import core.barcode.BarcodeGeneratorInterface;
import core.persistence.CustomerStore;
import core.user.Customer;
import io.swagger.api.impl.BarcodeResponse;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.mockito.Mockito;

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

        String uuidFromBase64 = uuidFromBase64(barcode);
        boolean validuuid = uuidFromBase64.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");

        //Assert
        assertTrue(validuuid);
        instance.clearStore();
    }

    //https://stackoverflow.com/a/15013205
    private static String uuidFromBase64(String str) {
        String base64Padding = "==";
        byte[] bytes = Base64.decodeBase64((str + base64Padding).getBytes());
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
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