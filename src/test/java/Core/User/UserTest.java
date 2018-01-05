package Core.User;
import static org.mockito.Mockito.*;

import Core.Barcode.BarcodeGeneratorInterface;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserTest {

    private  User user;
    @Before
    public void setUp() {
        user = new User("boris");
    }

    @Test
    public void requestBarcode() throws IOException {
        //Arrange

        //Act
        user.requestBarcode();

        //Assert
        assertNotNull(user.getBarcode());
    }


    @Rule
    public final  ExpectedException exception = ExpectedException.none();

    @Test
    public void requestBarcode_barcodeGenerationFailed() throws IOException {
        //Arrange
        BarcodeGeneratorInterface generatorMock = mock(BarcodeGeneratorInterface.class);

        when(generatorMock.generateBarcode()).thenThrow(new IOException());

        user.setBarcodeGenerator(generatorMock);

        //Act
        exception.expect(IOException.class);
        user.requestBarcode();

        //Assert
    }

    @Test
    public void checkUserProperties(){
        //Arrange

        //Act

        //Assert
        assertTrue(user.getName().length() > 0);
        assertNull(user.getBarcode());
        assertEquals(user.getUserID().toString().length(),36);
    }
}