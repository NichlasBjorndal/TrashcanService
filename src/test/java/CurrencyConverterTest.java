import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CurrencyConverterTest {
    private CurrencyConverter currencyConverter;
    private FileIO fileIO;

    private ArrayList<String> fileContents;

    private Double[] expectedConversionResults;
    private double conversionResult;

    @Before
    public void setUp() throws Exception {
        fileIO = new FileIO();

        expectedConversionResults = new Double[]{1000*0.42,500*0.69,1284*0.42,9587*0.69,239*0.69};

        currencyConverter = new CurrencyConverter();
    }

    @Test
    public void dkkConvertion() throws Exception {
        fileContents = fileIO.getInput();

        assertNotNull(fileContents);

        conversionResult = currencyConverter.convert(fileContents.get(1));

        assertEquals(expectedConversionResults[1],conversionResult,0.5);
    }

    @Test
    public void eurConvertion() throws Exception {
        fileContents = fileIO.getInput();

        assertNotNull(fileContents);

        conversionResult = currencyConverter.convert(fileContents.get(0));

        assertEquals(expectedConversionResults[0],conversionResult,0.5);
    }

    @Test
    public void convertAll() throws Exception {
        fileContents = fileIO.getInput();

        assertNotNull(fileContents);

        int i = 0;
        for (String s :
                fileContents) {
            conversionResult = currencyConverter.convert(s);
            assertEquals(conversionResult,expectedConversionResults[i],0.5);
            i++;
        }
    }
}