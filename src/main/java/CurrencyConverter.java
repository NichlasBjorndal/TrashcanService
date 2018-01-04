import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;

/**
 * Converts valuta to USD.
 */
class CurrencyConverter {
    private static final String CONVERSION_RATE_FILE_NAME = "ConvertionRates.txt";

    private HashMap<String,Double> conversionRates;

    CurrencyConverter() throws IOException {
        conversionRates = getConversionRates();
    }

    static HashMap<String, Double> getConversionRates() throws IOException {
        HashMap<String, Double> conversionRates = new HashMap<>();

        List<String> input = readAllLines(new File(CONVERSION_RATE_FILE_NAME).toPath());

        for (String s : input) {
            String[] rate = s.split(" ");

            conversionRates.put(rate[0], Double.parseDouble(rate[1]));
        }

        return conversionRates;
    }

    /**
     * @param currencyAmountString String containing currency and amount separated by whitespace..
     * @return Returns conversion to USD.
     */
    double convert(String currencyAmountString) {
        String[] currencyAmountPair = currencyAmountString.split(" ");
        double conversionRate = conversionRates.get(currencyAmountPair[0]);

        return  conversionRate * Double.parseDouble( currencyAmountPair[1]);
    }

    void convertFile(String inputFileName, String outputFileName) throws IOException {
        ArrayList<String> inputAmounts = (ArrayList<String>) readAllLines(new File(inputFileName).toPath());

        //Create output file.
        File file = new File(outputFileName);

        OutputStream os = newOutputStream(file.toPath());
        os.write(("").getBytes());

        //Calculate conversions and write to output file.
        for (String inputAmount : inputAmounts) {
            double outputAmount = convert(inputAmount);

            write(file.toPath(), (outputAmount + "\n").getBytes(), StandardOpenOption.APPEND);
        }
    }
}
