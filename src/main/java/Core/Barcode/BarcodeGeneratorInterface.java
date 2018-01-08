package Core.Barcode;

import Core.Barcode.Model.Barcode;

import java.io.IOException;

public interface BarcodeGeneratorInterface {
    Barcode generateBarcode() throws IOException;
}
