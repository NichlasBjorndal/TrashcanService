package mdb;

import core.FastMoneyTransaction;
import core.barcode.Model.Barcode;
import core.persistence.BarcodeStore;
import core.persistence.CustomerStore;
import core.persistence.MerchantStore;
import core.user.Customer;
import core.user.Merchant;
import core.utils.GsonWrapper;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.swagger.api.impl.PayResponse;
import io.swagger.model.Transaction;
import jsmprovider.JmsProvider;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import java.util.UUID;

@MessageDriven(name = "PayMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/PayQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class PayMDB extends BaseMDB {
    private static final String TRANSACTION_QUEUE = "FastMoneyBankTransactionQueue";

    @Override
    protected String processMessage(String receivedText) throws BankServiceException_Exception {
        Transaction transaction = (Transaction) GsonWrapper.fromJson(receivedText, Transaction.class);

        //Lookup customer CPR from barcode
        String customerCPR = getCostumer(transaction.getBarcode());
        if (customerCPR.equals("")) {
            return GsonWrapper.toJson(PayResponse.INVALID_BARCODE.getValue());
        }

        //Check that the merchant has account in DTUPay
        boolean validMerchant = validateMerchant(transaction.getReceiverCVR());
        if (!validMerchant) {
            return GsonWrapper.toJson(PayResponse.INVALID_MERCHANT.getValue());
        }

        //Put transaction in queue for the transaction bean
        JmsProvider jmsProvider = new JmsProvider();

        FastMoneyTransaction fastMoneyTransaction = new FastMoneyTransaction(customerCPR, transaction.getReceiverCVR(), transaction.getAmount(), "Transfer from " + customerCPR + " to " + transaction.getReceiverCVR());
        String body = GsonWrapper.toJson(fastMoneyTransaction);

        String transactionResponse;
        try {
            transactionResponse = jmsProvider.sendMessage(TRANSACTION_QUEUE, body);
        } catch (Exception e) {
            return GsonWrapper.toJson(PayResponse.SERVER_ERROR.getValue());
        }

        //Interpret transaction response
        String response;
        String parsedResponse = (String) GsonWrapper.fromJson(transactionResponse, String.class);
        if (parsedResponse.equals("Debtor balance will be negative")) {
            response = PayResponse.NOT_ENOUGH_FUNDS.getValue();
        } else if (parsedResponse.equals("Account does not exist")) {
            response = PayResponse.NO_BANK_ACCOUNT.getValue();
        } else {
            response = PayResponse.SUCCESSFUL_PAYMENT.getValue();
            removeBarcode(transaction.getBarcode());
        }
        return GsonWrapper.toJson(response);
    }

    private boolean validateMerchant(String receiverCVR) {
        MerchantStore merchantStore = MerchantStore.getInstance();
        Merchant merchant = merchantStore.getMerchant(receiverCVR);
        if (merchant == null) {
            return false;
        } else {
            return true;
        }

    }

    private void removeBarcode(String barcode) {
        BarcodeStore barcodeStore = BarcodeStore.getInstance();
        CustomerStore customerStore = CustomerStore.getInstance();
        UUID customerId = barcodeStore.getCustomerId(barcode);
        Customer customer = customerStore.getCustomer(customerId);
        for (Barcode b : customer.getBarcodes()) {
            if (barcode.equals(b.getUUID())) {
                customer.removeBarcode(b);
                break;
            }
        }
        barcodeStore.removeBarcode(barcode);
    }

    private String getCostumer(String barcode) {
        UUID customerId;
        CustomerStore customerStore;
        BarcodeStore barcodeStore = BarcodeStore.getInstance();
        customerId = barcodeStore.getCustomerId(barcode);
        customerStore = CustomerStore.getInstance();
        if (customerId == null){
            return "";
        }
        return customerStore.getCustomer(customerId).getCpr();
    }
}
