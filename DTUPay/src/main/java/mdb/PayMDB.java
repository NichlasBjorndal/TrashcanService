package mdb;

import core.persistence.BarcodeStore;
import core.persistence.CustomerStore;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.swagger.api.impl.PayApiServiceImpl;
import io.swagger.api.impl.PayResponse;
import io.swagger.model.Transaction;
import mdb.utils.BankServerUtil;
import mdb.utils.GsonWrapper;

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
            response = PayResponse.SUCCESSFUL_PAYMENT.getValue();
        } catch (BankServiceException_Exception e) {
            if (e.getMessage().equals("Debtor balance will be negative")) {
                response = PayResponse.INVALID_INPUT.getValue();
            } else {
                response = PayResponse.SUCCESSFUL_PAYMENT.getValue();
            }
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
