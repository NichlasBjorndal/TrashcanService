import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class CurrencyConverterTest {
    private CurrencyConverter uut  = new CurrencyConverter();

   /*
    public void setUp() {
        uut = new CurrencyConverter();
    }
*/
    @Test
    public void generateExchangeRate_Generation_GenerationSuccessful() {
        //Arrange

        //Act
        boolean DKKRateExists = uut.getExchangeRates().containsKey("DKK");
        boolean EURateExists = uut.getExchangeRates().containsKey("EUR");

        //Assert
        assertTrue(DKKRateExists);
        assertTrue(EURateExists);
    }


    @Test
    public void readFile_ValidFileName_ListOfCurrencies() {
        //Arrange

        //Act
        List<String> strings = uut.ReadFile("currencies.txt");

        //Assert
        assertTrue(strings.size() > 0);

    }

    @Test
    public void processStrings() {
        // Arrange
        LinkedList<String> toProcessLinkedList = new LinkedList<>();
        toProcessLinkedList.add("DKK 100");
        toProcessLinkedList.add("EUR 500");

        Double DKKCurrency = uut.getExchangeRates().get("DKK");
        Double EURCurrency = uut.getExchangeRates().get("EUR");

        LinkedList<String> expectedLinkedList = new LinkedList<>();
        expectedLinkedList.add(Double.toString(DKKCurrency * 100));
        expectedLinkedList.add(Double.toString(EURCurrency * 500));

        // Act
        List<String> generatedList = uut.ProcessStrings(toProcessLinkedList);

        // Assert
        assertEquals(expectedLinkedList, generatedList);
    }

    @Test
    public void ConvertCurrencyFile() {
        //Arrange
        String originalFileName = "currencies.txt";
        String outputFileName = "result.txt";

        LinkedList<String> expectedConvertedNumbers = new LinkedList<>();
        expectedConvertedNumbers.add("750.0");
        expectedConvertedNumbers.add("600.0");

        //Act
        uut.ConvertCurrencyFile(originalFileName, outputFileName);


        List<String> convertedNumbers = uut.ReadFile(outputFileName);

        //Assert
        assertEquals(expectedConvertedNumbers, convertedNumbers);
    }

}