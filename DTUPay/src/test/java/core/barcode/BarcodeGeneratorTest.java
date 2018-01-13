package core.barcode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import core.barcode.Model.Barcode;

public class BarcodeGeneratorTest {
    private BarcodeGenerator barcodeGenerator;
    private Barcode barcodeResult;

    @Before
    public void setUp() throws Exception {
        barcodeGenerator = new BarcodeGenerator();
    }

    @After
    public void tearDown() throws Exception {
        if(barcodeResult.getFile() != null)
            barcodeResult.getFile().delete();
    }

    @Test
    public void generateBarcode() throws Exception {

        barcodeResult = barcodeGenerator.generateBarcode();

        assertNotNull(barcodeResult);

        assertTrue(barcodeResult.getFile().getName().length() > 0);

        assertEquals(22,barcodeResult.getUUID().length());

        assertTrue(barcodeResult.getFile().exists());
    }
}