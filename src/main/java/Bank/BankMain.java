package dtu.ws.fastmoney;

import java.math.BigDecimal;

public class BankMain {
    public static void main(String[] args) throws BankServiceException_Exception {
        BankServiceService service = new BankServiceService();
        BankService server = service.getBankServicePort();

        User u = new User();
        u.setFirstName("gfdshgfa");
        u.setLastName("Mgfdagda");
        u.setCprNumber("0000000000");

        BigDecimal bD = new BigDecimal(0.0);

        String user = server.createAccountWithBalance(u,bD);
        System.out.println(user);
    }
}
