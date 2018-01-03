import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CurrencyConverter cc = new CurrencyConverter();
        ArrayList<String> contents = FileReader.getInput();
        for (String s :
                contents) {
            System.out.println(cc.convert(s));
        }
    }
}