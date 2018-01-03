import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Prints "Hello, World" in the terminal window.
        CurrencyConverter currencyConverter = new CurrencyConverter();

        List<String> strings = currencyConverter.ReadFile("currencies.txt");
        List<String> processedStings = currencyConverter.ProcessStrings(strings);

    }
}