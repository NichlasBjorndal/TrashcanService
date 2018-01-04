import java.util.HashMap;

/**
 * Converts valuta to USD.
 */
class CurrencyConverter {

    private HashMap<String,Double> conversionRates;

    CurrencyConverter(){
        conversionRates = FileIO.getConversionRates();
    }

    /**
     * @param s String containing currency and amount.
     * @return Returns conversion to USD.
     */
    double convert(String s) {
        String[] value = s.split(" ");
        double rate = conversionRates.get(value[0]);

        return  rate * Double.parseDouble( value[1]);
    }
}
