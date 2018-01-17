package mdb;

import core.utils.GsonWrapper;
import mdb.utils.BarcodeMessageHandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

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
        String response = BarcodeMessageHandler.createBarcode(inputUUID);

        return GsonWrapper.toJson(response);
    }
}
