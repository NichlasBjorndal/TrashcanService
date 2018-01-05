package Core.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private  User user;
    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void requestBarcode() {
        //Arrange

        //Act
        user.requestBarcode();

        //Assert
        assertNotNull(user.getBarcode());
    }
}