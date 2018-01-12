package core.user;


import core.barcode.BarcodeGenerator;
import core.barcode.BarcodeGeneratorInterface;
import core.barcode.Model.Barcode;

import java.io.IOException;
import java.util.UUID;

/**
 *  An object defining the user
 */
public class User {

    private Barcode barcode;
    private String name;
    private UUID userID;
    private transient BarcodeGeneratorInterface barcodeGenerator;
    private String cpr;

    /**
     * @param name defining the user's full name
     * @param cpr defining the user's cpr-number
     */
    public User(String name, String cpr){
        this.cpr = cpr;
        this.barcodeGenerator = new BarcodeGenerator();
        this.userID = UUID.randomUUID();
        this.name = name;
    }

    //Used for GSON
    public User(){
        this.barcodeGenerator = new BarcodeGenerator();
        this.userID = UUID.randomUUID();
    }

    /** requests barcode from barcode generator
     * @throws IOException
     */
    public void requestBarcode() throws IOException {
        barcode = barcodeGenerator.generateBarcode();
    }

    public Barcode getBarcode() {
        return barcode;
    }


    /**
     * @return the user's full name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the user's unique ID
     */
    public UUID getUserID() {
        return userID;
    }


    public void setBarcodeGenerator(BarcodeGeneratorInterface barcodeGenerator) {
        this.barcodeGenerator = barcodeGenerator;
    }


    /**
     * @return the user's cpr-number
     */
    public String getCpr() {
        return cpr;
    }
}

