package Barcode;

import Barcode.Model.Barcode;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;


public class BarcodeGenerator {

    private UniqueIDGenerator uniqueIDGenerator;

    public BarcodeGenerator(){
        uniqueIDGenerator = new UniqueIDGenerator();
    }

    public Barcode generateBarcode() throws IOException {
        String uuid = uniqueIDGenerator.generateUUID();


        Code128Bean barcode128Bean = new Code128Bean();

        barcode128Bean.setCodeset(Code128Constants.CODESET_B);
        final int dpi = 500;

        //Configure the barcode generator
        //adjust barcode width here
        barcode128Bean.setModuleWidth(UnitConv.in2mm(5.0f / dpi));
        barcode128Bean.doQuietZone(true);

        //Open output file
        File outputFile = new File(uuid + ".png");
        OutputStream out = new FileOutputStream(outputFile);
        try
        {
            BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(
                    out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            barcode128Bean.generateBarcode(canvasProvider,uuid);

            canvasProvider.finish();
        }
        finally
        {
            out.close();
        }

        return new Barcode(outputFile, uuid);
    }
}
