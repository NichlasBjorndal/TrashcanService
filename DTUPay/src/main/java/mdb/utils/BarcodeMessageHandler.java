package mdb.utils;

import core.barcode.BarcodeGenerator;
import core.barcode.Model.Barcode;
import core.user.Customer;
import io.swagger.api.impl.BarcodeResponse;
import persistence.BarcodeStore;
import persistence.CustomerStore;

import java.io.IOException;
import java.util.UUID;

public class BarcodeMessageHandler {
    public static String createBarcode(String inputUUID) {
        if (!isUUIDValid(inputUUID)) {
            return BarcodeResponse.INVALID_INPUT.getValue();
        } else if (!uuidIsUserId(inputUUID)) {
            return BarcodeResponse.NO_USER.getValue();
        } else {
            BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
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

    private static boolean isUUIDValid(String inputUUID) {
        return inputUUID.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
    }

    private static boolean uuidIsUserId(String uuid) {
        CustomerStore instance = CustomerStore.getInstance();
        Customer customer = instance.getCustomer(UUID.fromString(uuid));
        return customer != null;
    }
}