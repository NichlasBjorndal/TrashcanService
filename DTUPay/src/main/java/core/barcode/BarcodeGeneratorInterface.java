package core.barcode;

import core.barcode.Model.Barcode;

import java.io.IOException;

/**
 * @author Nichlas Bjørndal
 * Interface for barcode generation.
 */
public interface BarcodeGeneratorInterface {
    Barcode generateBarcode() throws IOException;
}
