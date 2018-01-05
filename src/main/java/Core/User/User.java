package Core.User;


import Core.Barcode.BarcodeGenerator;
import Core.Barcode.BarcodeGeneratorInterface;
import Core.Barcode.Model.Barcode;

import java.io.IOException;
import java.util.UUID;

public class User {

    private Barcode barcode;
    private String name;
    private UUID userID;
    private BarcodeGeneratorInterface barcodeGenerator;
    private String cpr;

    public User(String name, String cpr){
        this.cpr = cpr;
        this.barcodeGenerator = new BarcodeGenerator();
        this.userID = UUID.randomUUID();
        this.name = name;
    }

    public void requestBarcode() throws IOException {
        barcode = barcodeGenerator.generateBarcode();
    }

    public Barcode getBarcode() {
        return barcode;
    }


    public String getName() {
        return name;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setBarcodeGenerator(BarcodeGeneratorInterface barcodeGenerator) {
        this.barcodeGenerator = barcodeGenerator;
    }

    public String getCpr() {
        return cpr;
    }
}

