import java.util.ArrayList;
import java.util.HashMap;

public class CurrencyConverter {

    private HashMap<String,Double> conversionRates;

    public CurrencyConverter(){
        conversionRates = FileReader.getConversionRates();
    }

    public double convert(String s) {
        String[] value = s.split(" ");
        double rate = conversionRates.get(value[0]);

        return  rate * Double.parseDouble( value[1]);
    }
}
