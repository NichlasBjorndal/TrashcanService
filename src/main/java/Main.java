import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CurrencyConverter cc = new CurrencyConverter();
        ArrayList<String> contents = FileIO.getInput();
        FileIO.clearOutputFile();
        for (String s :
                contents) {
            Double result = cc.convert(s);
            System.out.println(result);
            FileIO.writeConversion(result.toString());
        }
    }
}