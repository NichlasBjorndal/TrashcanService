package dtu.ws.fastmoney;

import java.math.BigDecimal;
import java.util.List;

public class BankMain {
    public static void main(String[] args) throws BankServiceException_Exception {
        BankServiceService service = new BankServiceService();
        BankService server = service.getBankServicePort();

        User u = new User();
        u.setCprNumber("0802558720");
        u.setLastName("Petersen");
        u.setFirstName("Peter");

        BigDecimal bD = new BigDecimal(1000.0);

        String user = server.createAccountWithBalance(u,bD);
        System.out.println(user);

        //server.retireAccount(user);
        //List<AccountInfo> ac = server.getAccounts();

//        for (AccountInfo a: ac) {
  //          System.out.println(a.accountId);
    //        System.out.println(a.user.firstName);
      //  }

        BigDecimal bD1 = new BigDecimal(500.0);

        System.out.println("Balance before: " + server.getAccount("6ab8e8cc-b729-4180-86ee-46ef775705fd").getBalance());

        server.transferMoneyFromTo(user,"6ab8e8cc-b729-4180-86ee-46ef775705fd",bD1, "YOLO");

        System.out.println("Balance after: " + server.getAccount("6ab8e8cc-b729-4180-86ee-46ef775705fd").getBalance());




    }
}
