package mdb;

import core.user.Customer;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import io.swagger.api.impl.PayApiServiceImpl;
import io.swagger.model.Transaction;
import mdb.utils.BankServerUtil;
import mdb.utils.GsonWrapper;
import persistence.BarcodeStore;
import persistence.CustomerStore;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import java.util.UUID;

@MessageDriven(name = "PayMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/PayQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class PayMDB extends BaseMDB {

    @Override
    protected String processMessage(String receivedText) throws BankServiceException_Exception {
        Transaction transaction = (Transaction) GsonWrapper.fromJson(receivedText, Transaction.class);

        String customerCPR = getCostumer(transaction.getBarcode());

        BankService server = BankServerUtil.getServer();

        String response;
        try {
            String senderAccountId = server.getAccountByCprNumber(customerCPR).getId();
            String receiverAccountId = server.getAccountByCprNumber(transaction.getReceiverCVR()).getId();
            server.transferMoneyFromTo(senderAccountId, receiverAccountId, transaction.getAmount(),"Successful transaction");
            response = PayApiServiceImpl.SUCCESSFUL_PAY;
        } catch (BankServiceException_Exception e) {
            response = "failure";
        }

        return GsonWrapper.toJson(response);
    }

    private String getCostumer(String barcode) {
        BarcodeStore barcodeStore = BarcodeStore.getInstance();
        UUID customerId = barcodeStore.getCustomerId(barcode);
        CustomerStore customerStore = CustomerStore.getInstance();
        return customerStore.getCustomer(customerId).getCpr();
    }
}