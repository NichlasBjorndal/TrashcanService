import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;

class FileIO {
    private static final String INPUT_FILE_NAME = "Input.txt";
    static final String OUTPUT_FILE_NAME = "Output.txt";
    static final String CONVERSION_RATE_FILE_NAME = "ConvertionRates.txt";

    public static ArrayList<String> getInput() {
        try {
            return (ArrayList<String>) readAllLines(new File(INPUT_FILE_NAME).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeConversion(String s) {
        File file = new File(OUTPUT_FILE_NAME);

        try {
            write(file.toPath(), (s+"\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearOutputFile() {
        File file = new File(OUTPUT_FILE_NAME);

        try {
            OutputStream os = newOutputStream(file.toPath());
            os.write(("").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Conversion rates in a specified file.
     */
    public static HashMap<String,Double> getConversionRates() {
        HashMap<String,Double> conversionRates = new HashMap<>();
        try {
            List<String> input = readAllLines(new File(CONVERSION_RATE_FILE_NAME).toPath());

            for (String s : input) {
                String[] rate = s.split(" ");

                conversionRates.put(rate[0],Double.parseDouble(rate[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conversionRates;
    }
}