import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            CurrencyConverter cc = new CurrencyConverter();
            ArrayList<String> contents = null;

            contents = FileIO.getInput();
            FileIO.clearOutputFile();

            for (String s : contents) {
                Double result = cc.convert(s);
                System.out.println(result);
                FileIO.writeConversion(result.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}