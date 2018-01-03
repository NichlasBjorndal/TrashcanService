import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class FileReader {
    public static final String INPUT_FILE_NAME = "Input.txt";
    public static final String CONVERSION_RATE_FILE_NAME = "ConvertionRates.txt";

    public static ArrayList<String> getInput() {
        try {
            return (ArrayList<String>) readAllLines(new File(INPUT_FILE_NAME).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

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