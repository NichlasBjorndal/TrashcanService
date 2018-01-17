package mdb.utils;

import core.barcode.BarcodeGenerator;
import core.barcode.BarcodeGeneratorInterface;
import core.barcode.Model.Barcode;
import core.user.Customer;
import io.swagger.api.impl.BarcodeResponse;
import core.persistence.BarcodeStore;
import core.persistence.CustomerStore;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Mikkel Brusen
 * Contains methods for handling messages received on barcode MDBs.
 */
public class BarcodeMessageHandler {
    /**
     * Generates a Barcode and attaches it to the customer in the CustomerStore. It also creates a mapping from barcode
     * to customer in the BarcodeStore, which enables the identification of a customer from a Barcode UUID
     * @param inputUUID String of the UUID of the customer for whom the barcode is being generated for.
     * @param barcodeGeneratorInterface This a seam where it's possible to inject a custom barcode generator
     * @return UUID for the barcode that has been generated or error message if relevant.
     */
    public static String createBarcode(String inputUUID, BarcodeGeneratorInterface barcodeGeneratorInterface) {
        if (!isUUIDValid(inputUUID)) {
            return BarcodeResponse.INVALID_INPUT.getValue();
        } else if (!uuidIsUserId(inputUUID)) {
            return BarcodeResponse.NO_USER.getValue();
        } else {
            BarcodeGeneratorInterface barcodeGenerator = barcodeGeneratorInterface;
            UUID uuid = UUID.fromString(inputUUID);

            Barcode barcode = null;
            try {
                barcode = barcodeGenerator.generateBarcode();
            } catch (IOException e) {
                e.printStackTrace();
            }

            CustomerStore customerStoreInstance = CustomerStore.getInstance();
            Customer customer = customerStoreInstance.getCustomer(uuid);

            customer.getBarcodes().add(barcode);

            BarcodeStore barcodeStoreInstance = BarcodeStore.getInstance();
            barcodeStoreInstance.saveBarcode(barcode, customer);

            return barcode.getUUID();
        }
    }

    /**
     * createBarcode method using the BarcodeGenerator supplied in the core.barcode package
     * @param inputUUID String of the UUID of the user for whom the barcode is being generated for.
     * @return UUID for the barcode that has been generated or error message if relevant.
     */
    public static String createBarcode(String inputUUID) {
        return BarcodeMessageHandler.createBarcode(inputUUID, new BarcodeGenerator());
    }

    /**
     * @param inputUUID String value of an UUID.
     * @return Whether the structure of the UUID is valid.
     */
    private static boolean isUUIDValid(String inputUUID) {
        return inputUUID != null && inputUUID.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
    }

    /**
     * @param uuid String value of a customer UUID.
     * @return whether the input UUID matches an existing customer's UUID.
     */
    private static boolean uuidIsUserId(String uuid) {
        CustomerStore instance = CustomerStore.getInstance();
        Customer customer = instance.getCustomer(UUID.fromString(uuid));
        return customer != null;
    }
}