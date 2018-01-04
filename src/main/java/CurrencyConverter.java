import java.io.IOException;
import java.util.HashMap;

/**
 * Converts valuta to USD.
 */
class CurrencyConverter {

    private HashMap<String,Double> conversionRates;

    CurrencyConverter() throws IOException {
        conversionRates = FileIO.getConversionRates();
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
}
