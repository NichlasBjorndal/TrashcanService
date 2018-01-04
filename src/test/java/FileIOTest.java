import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static java.nio.file.Files.readAllLines;
import static org.junit.Assert.*;

public class FileIOTest {
    private ArrayList<String> fileContents;
    private HashMap<String,Double> conversionRates;
    private ArrayList<String> expectedFileContents;
    private HashMap<String,Double> expectedConversionRates;
    private String output;

    @Before
    public void setUp() throws Exception {
        fileContents = null;

        FileIO.clearOutputFile();

        expectedFileContents = new ArrayList<>();
        expectedFileContents.add("EUR 1000");
        expectedFileContents.add("DKK 500");
        expectedFileContents.add("EUR 1284");
        expectedFileContents.add("DKK 9587");
        expectedFileContents.add("DKK 239");

        expectedConversionRates = new HashMap<>();
        expectedConversionRates.put("EUR", 0.42);
        expectedConversionRates.put("DKK", 0.69);
    }

    @Test
    public void readInputFileContents() throws Exception {
        assertNull(fileContents);

        fileContents = FileIO.getInput();

        assertNotNull(fileContents);
    }

    @Test
    public void writeOutput() throws Exception {
        assertNull(output);
        output = "EUR 239";

        FileIO.writeConversion(output);

        assertEquals(output,readAllLines(new File(FileIO.OUTPUT_FILE_NAME).toPath()).get(0));
    }

    @Test
    public void checkInputFileContents() throws Exception {
        assertNull(fileContents);

        fileContents = FileIO.getInput();

        assertEquals(expectedFileContents,fileContents);
    }

    @Test
    public void readConversionRates() throws Exception {
        assertNull(conversionRates);

        conversionRates = FileIO.getConversionRates();

        assertNotNull(conversionRates);
    }

    @Test
    public void checkConversionRates() throws Exception {
        assertNull(conversionRates);

        conversionRates = FileIO.getConversionRates();

        assertEquals(expectedConversionRates,conversionRates);
    }
}