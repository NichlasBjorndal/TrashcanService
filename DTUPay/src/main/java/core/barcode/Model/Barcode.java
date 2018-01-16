package core.barcode.Model;

import java.io.File;

/**
 *  An object containing the barcode image file and uuid
 */
public class Barcode {
    private File barcodeFile;
    private String uuid;

    /**
     * @param barcodeFile barcode image file
     * @param uuid unique ID stored in bar code
     */
    public Barcode(File barcodeFile, String uuid){
        this.uuid = uuid;
        this.barcodeFile = barcodeFile;
    }

    public Barcode(){
    }

    /**
     * @return the barcode image file
     */
    public File getFile() {
        return barcodeFile;
    }

    /**
     * @return the unique ID of the barcode
     */
    public String getUUID() {
        return uuid;
    }
}
