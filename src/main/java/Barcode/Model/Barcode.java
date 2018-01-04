package Barcode.Model;

import java.io.File;

public class Barcode {
    File barcodeFile;
    String uuid;

    public Barcode(File barcodeFile, String uuid){
        this.uuid = uuid;
        this.barcodeFile = barcodeFile;
    }

    public File getFile() {
        return barcodeFile;
    }

    public String getUUID() {
        return uuid;
    }
}
