import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;

/**
 * Handles writing and reading of input/output files.
 */
class FileIO {
    private static final String INPUT_FILE_NAME = "Input.txt";
    static final String OUTPUT_FILE_NAME = "Output.txt";
    private static final String CONVERSION_RATE_FILE_NAME = "ConvertionRates.txt";

    /**
     * @return Each line contained in a list as a string.
     */
    static ArrayList<String> getInput() throws IOException {
        return (ArrayList<String>) readAllLines(new File(INPUT_FILE_NAME).toPath());
    }

    /**
     * @param s conversion to be written to output file.
     */
    static void writeConversion(String s) throws IOException {
        File file = new File(OUTPUT_FILE_NAME);
        write(file.toPath(), (s + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    /**
     * Clears output file contents.
     */
    static void clearOutputFile() throws IOException {
        File file = new File(OUTPUT_FILE_NAME);

        OutputStream os = newOutputStream(file.toPath());
        os.write(("").getBytes());
    }

    /**
     * @return Conversion rates in a specified file.
     */
    static HashMap<String, Double> getConversionRates() throws IOException {
        HashMap<String, Double> conversionRates = new HashMap<>();

        List<String> input = readAllLines(new File(CONVERSION_RATE_FILE_NAME).toPath());

        for (String s : input) {
            String[] rate = s.split(" ");

            conversionRates.put(rate[0], Double.parseDouble(rate[1]));
        }

        return conversionRates;
    }
}