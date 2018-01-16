package mdb;

import core.barcode.BarcodeGenerator;
import core.barcode.Model.Barcode;
import core.user.Customer;
import core.utils.GsonWrapper;
import io.swagger.api.impl.BarcodeResponse;
import persistence.BarcodeStore;
import persistence.CustomerStore;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import java.io.IOException;
import java.util.UUID;

/**
 * Message Driven Bean for handling barcode requests. Listens to the RequestBarcodeQueue.
 */
@MessageDriven(name = "RequestBarcodeMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/RequestBarcodeQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class RequestBarcodeMDB extends BaseMDB {
    /**
     * @param receivedText JSON received from RequestBarcodeQueue.
     * @return new barcode uuid if successful, otherwise relevant error message.
     */
    @Override
    protected String processMessage(String receivedText) {
        String inputUUID = (String) GsonWrapper.fromJson(receivedText, String.class);

        boolean validUUID = true;
        UUID uuid = null;
        try {
            uuid = UUID.fromString(inputUUID);
        } catch (Exception e) {
            validUUID = false;
        }

        String response;

        if (!validUUID ){
            response = BarcodeResponse.INVALID_INPUT.getValue();
        } else if(!uuidIsUserId(uuid)){
            response = BarcodeResponse.NO_USER.getValue();
        }
        else {
            BarcodeGenerator barcodeGenerator = new BarcodeGenerator();

            //TODO properly handle IO exception
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

            response = barcode.getUUID();
        }
        return GsonWrapper.toJson(response);
    }

    private boolean uuidIsUserId(UUID uuid) {
        CustomerStore instance = CustomerStore.getInstance();

        Customer customer = instance.getCustomer(uuid);

        return customer != null;

    }
}
