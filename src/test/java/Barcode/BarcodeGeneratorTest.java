package Barcode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import Barcode.Model.Barcode;

public class BarcodeGeneratorTest {
    private BarcodeGenerator barcodeGenerator;

    @Before
    public void setUp() throws Exception {
        barcodeGenerator = new BarcodeGenerator();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generateBarcode() throws Exception {
        Barcode barcodeResult;

        barcodeResult = barcodeGenerator.generateBarcode();

        assertNotNull(barcodeResult);

        assertTrue(barcodeResult.getFile().getName().length() > 0);

        assertEquals(36,barcodeResult.getUUID().length());

        assertTrue(barcodeResult.getFile().exists());


    }
}