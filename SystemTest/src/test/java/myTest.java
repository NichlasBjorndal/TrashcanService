import Core.User.User;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class myTest {

    @Test
    public void generateBarcode() {


        User user = new User("karl", "1234");
        assertTrue(user != null);

    }
}
