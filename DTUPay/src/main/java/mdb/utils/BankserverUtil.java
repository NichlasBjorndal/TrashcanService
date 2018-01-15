package mdb.utils;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;

public class BankserverUtil {
    public static BankService GetServer(){
        BankServiceService service = new BankServiceService();
        return service.getBankServicePort();
    }
}
