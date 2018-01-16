package core.persistence;

import core.barcode.Model.Barcode;
import core.user.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * @param customer
     * @param barcode
     */
    public void saveBarcode( Barcode barcode, Customer customer){
        barcodes.put(barcode.getUUID(), customer.getUserID());
    }


    /**
     * @param barcodeuuid
     * @return
     */
    public UUID getCustomerId(String barcodeuuid){
        return barcodes.get(barcodeuuid);
    }


    /**
     * Deletes all users from the store
     */
    public void clearStore(){
        barcodes.clear();
    }

    /**
     * @return
     */
    public Map<String, UUID> getBarcodes(){
        return barcodes;
    }

    public void removeBarcode(String barcode) {
        barcodes.remove(barcode);
    }
}
