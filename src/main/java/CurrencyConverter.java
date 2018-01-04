import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CurrencyConverter {

    private HashMap<String, Double> exchangeRates;


    /**
     * Generates a map of currency exchange rates relative to the USD
     */
    public CurrencyConverter(){
        exchangeRates = GenerateExchangeRate();
    }

    public HashMap<String, Double> GenerateExchangeRate() {
        HashMap<String, Double> exchangeMap = new HashMap<String, Double>();

        exchangeMap.put("DKK", 7.5);
        exchangeMap.put("EUR", 1.2);

        return exchangeMap;
    }

    public HashMap<String, Double> getExchangeRates() {
        return exchangeRates;
    }

    /**
     * @param fileName name of file that contains currencies
     * @return a list of amounts of currency to be exchange
     */
    public List<String> ReadFile(String fileName){

        LinkedList<String> stringLinkedList = new LinkedList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach((line) -> stringLinkedList.add(line));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringLinkedList;
    }

    public List<String> ProcessStrings(List<String> currencies){

        LinkedList processedList = new LinkedList<>();

        for (String currency: currencies) {
            String[] split = currency.split(" ");
            String Country = split[0];
            Double Amount = Double.parseDouble(split[1]);

            Double CountryRate = exchangeRates.get(Country);
            Double ConvertedAmount = Amount * CountryRate;

            processedList.add(ConvertedAmount.toString());
        }

        return processedList;
    }

    public void printConvertedValues(List<String> values, String outFileName){
        StringBuilder builder = new StringBuilder();

        for (String element: values) {
            builder.append(element + "\n");
        }

        String builtString = builder.toString();

        try {
            Files.write(Paths.get(outFileName), builtString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ConvertCurrencyFile(String OriginalFileName, String outputFileName){
        List<String> strings = ReadFile(OriginalFileName);
        List<String> processedStings = ProcessStrings(strings);
        printConvertedValues(processedStings, outputFileName);
    }
}
