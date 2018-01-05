import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CurrencyConverterTest {
    private CurrencyConverter currencyConverter;

    private double conversionResult;

    @Before
    public void setUp() throws Exception {
        currencyConverter = new CurrencyConverter();
    }

    @Test
    public void dkkConversion() throws Exception {
        conversionResult = currencyConverter.convert("EUR 1000");

        assertEquals(1000*0.42,conversionResult,0.5);
    }

    @Test
    public void eurConversion() throws Exception {
        conversionResult = currencyConverter.convert("DKK 500");

        assertEquals(500*0.69,conversionResult,0.5);
    }

    @Test
    public void convertFile() throws Exception {
        File inputFile = new File("Input.txt");

        OutputStream os = newOutputStream(inputFile.toPath());
        os.write(("").getBytes());
        
        assertNotNull(inputFile);

        Double[] expectedConversionResults = new Double[]{1000*0.42,500*0.69,420*0.42,230*0.69};

        ArrayList<String> inputFileContents = new ArrayList<>();
        inputFileContents.add("EUR 1000");
        inputFileContents.add("DKK 500");
        inputFileContents.add("EUR 420");
        inputFileContents.add("DKK 230");

        for (String amount : inputFileContents) {
            write(inputFile.toPath(), (amount + "\n").getBytes(), StandardOpenOption.APPEND);
        }

        currencyConverter.convertFile("Input.txt","Output.txt");

        File outputFile = new File("Output.txt");

        ArrayList<String> resultingConversions = (ArrayList<String>) readAllLines(outputFile.toPath());

        assertEquals(4,resultingConversions.size());

        int i = 0;
        for (String conversionResult : resultingConversions) {
            assertEquals(expectedConversionResults[i],Double.parseDouble(conversionResult),0.5);
            i++;
        }
    }
}