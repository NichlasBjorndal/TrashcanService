package Core.User;


import Core.Barcode.BarcodeGenerator;
import Core.Barcode.BarcodeGeneratorInterface;
import Core.Barcode.Model.Barcode;

import java.io.IOException;


public class User {

    private Barcode barcode;

    public BarcodeGeneratorInterface getBarcodeGenerator() {
        return barcodeGenerator;
    }

    public void setBarcodeGenerator(BarcodeGeneratorInterface barcodeGenerator) {
        this.barcodeGenerator = barcodeGenerator;
    }

    private BarcodeGeneratorInterface barcodeGenerator;

    public User(){
        barcodeGenerator = new BarcodeGenerator();
    }

    public void requestBarcode(){
        try {
            barcode = barcodeGenerator.generateBarcode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Barcode getBarcode() {
        return barcode;
    }



}

