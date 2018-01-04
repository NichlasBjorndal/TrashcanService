package Barcode;

import Barcode.Model.Barcode;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Generates 128 code barcodes based on Base64 UUIDs.
 */
class BarcodeGenerator {

    private UniqueIDGenerator uniqueIDGenerator;

    BarcodeGenerator(){
        uniqueIDGenerator = new UniqueIDGenerator();
    }

    Barcode generateBarcode() throws IOException {
        String uuid = uniqueIDGenerator.generateUUIDToBase64();
        Code128Bean barcode128Bean = new Code128Bean();

        barcode128Bean.setCodeset(Code128Constants.CODESET_B);
        final int dpi = 500;

        //Configure barcode generator.
        //Set width an enable quiet zone.
        barcode128Bean.setModuleWidth(UnitConv.in2mm(5.0f / dpi));
        barcode128Bean.doQuietZone(true);

        //Create output .png file.
        File outputFile = new File(uuid + ".png");
        try (OutputStream out = new FileOutputStream(outputFile)) {
            BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(
                    out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            barcode128Bean.generateBarcode(canvasProvider, uuid);

            canvasProvider.finish();
        }

        return new Barcode(outputFile, uuid);
    }
}