package mdb;

import core.user.Customer;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import io.swagger.model.Transaction;
import mdb.utils.GsonWrapper;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(name = "PayMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/PayQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class PayMDB extends BaseMDB {



    @Override
    protected String processMessage(String receivedText) throws BankServiceException_Exception {
        Transaction transaction = (Transaction) GsonWrapper.fromJson(receivedText, Transaction.class);

        String customerCPR = getCostumer(transaction.getBarcode());

        BankServiceService service = new BankServiceService();
        BankService server = service.getBankServicePort();

        String senderAccountId = server.getAccountByCprNumber(customerCPR).getId();
        String receiverAccountId = server.getAccountByCprNumber(transaction.getReceiverCVR()).getId();
        server.transferMoneyFromTo(senderAccountId,receiverAccountId,transaction.getAmount(),"Successful transaction");
        String response = "Successful transaction";

        return GsonWrapper.toJson(response);
    }

    private String getCostumer(String barcode) {
        return "9859526433";
    }
}
