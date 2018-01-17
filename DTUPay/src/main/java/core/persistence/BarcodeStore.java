package core.persistence;

import core.barcode.Model.Barcode;
import core.user.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The store used for the Barcode UUID -> Customer mapping. This store allows one to find the customer UUID
 * to whom the Barcode is valid for
 */
public class BarcodeStore {
    private Map<String, UUID> barcodes;

    private static final BarcodeStore instance = new BarcodeStore();

    private BarcodeStore(){
        barcodes= new HashMap<>();
    }

    /**
     * @return A singleton instance of the object
     */
    public static BarcodeStore getInstance(){
        return instance;
    }

    /**
     * @param customer The Customer object to whom the Barcode is attached
     * @param barcode The Barcode object that is generated to the Customer
     */
    public void saveBarcode( Barcode barcode, Customer customer){
        barcodes.put(barcode.getUUID(), customer.getUserID());
    }


    /**
     * @param barcodeuuid The UUID of the Barcode object
     * @return The UUID of the customer to whom the
     */
    public UUID getCustomerId(String barcodeuuid){
        return barcodes.get(barcodeuuid);
    }


    /**
     * Deletes all mappings from the store
     */
    public void clearStore(){
        barcodes.clear();
    }

    /**
     * @return the map containing the barcode -> user UUID mapping in the BarcodeStore
     */
    public Map<String, UUID> getBarcodes(){
        return barcodes;
    }

    /**
     * @param barcode the barcode to remove from the store
     */
    public void removeBarcode(String barcode) {
        barcodes.remove(barcode);
    }
}
