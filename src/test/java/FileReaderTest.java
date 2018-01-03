import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class FileReaderTest {
    private FileReader fileReader;
    private ArrayList<String> fileContents;
    private HashMap<String,Double> conversionRates;
    private ArrayList<String> expectedFileContents;
    private HashMap<String,Double> expectedConversionRates;

    @Before
    public void setUp() throws Exception {
        fileReader = new FileReader();
        fileContents = null;

        expectedFileContents = new ArrayList<String>();
        expectedFileContents.add("EUR 1000");
        expectedFileContents.add("DKK 500");
        expectedFileContents.add("EUR 1284");
        expectedFileContents.add("DKK 9587");
        expectedFileContents.add("DKK 239");

        expectedConversionRates = new HashMap<String, Double>();
        expectedConversionRates.put("EUR", 0.42);
        expectedConversionRates.put("DKK", 0.69);
    }

    @Test
    public void readInputFileContents() throws Exception {
        assertNull(fileContents);

        fileContents = fileReader.getInput();

        assertNotNull(fileContents);
    }

    @Test
    public void checkInputFileContents() throws Exception {
        assertNull(fileContents);

        fileContents = fileReader.getInput();

        assertEquals(expectedFileContents,fileContents);
    }

    @Test
    public void readConversionRates() throws Exception {
        assertNull(conversionRates);

        conversionRates = fileReader.getConversionRates();

        assertNotNull(conversionRates);
    }

    @Test
    public void checkConversionRates() throws Exception {
        assertNull(conversionRates);

        conversionRates = fileReader.getConversionRates();

        assertEquals(expectedConversionRates,conversionRates);
    }
}