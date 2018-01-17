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

/**
 * Message Driven Bean to do payment
 */
@MessageDriven(name = "PayMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/PayQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class PayMDB extends BaseMDB {
    private static final String TRANSACTION_QUEUE = "FastMoneyBankTransactionQueue";

    /**Proccessing the transaction. Customer is found from barcode,
     * merchant is validated,
     * the transaction is put in FastMoneyBankTransactionQueue and
     * the reponse from TransferMoneyMDB is interpreted
     * @param receivedText the text message in JSON format containing the Transaction information
     * @return string in JSON format holding the response code and message of the transaction
     * @throws BankServiceException_Exception
     */
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
        } else if (parsedResponse.equals("Amount must be positive")) {
            response = PayResponse.NOT_ENOUGH_FUNDS.getValue();
        } else {
            response = PayResponse.SUCCESSFUL_PAYMENT.getValue();
            removeBarcode(transaction.getBarcode());
        }
        return GsonWrapper.toJson(response);
    }

    /**
     * @param receiverCVR the CVR number of the merchant.
     * @return boolaen indicating whether a merchant with the CVR number exists in the system
     */
    private boolean validateMerchant(String receiverCVR) {
        MerchantStore merchantStore = MerchantStore.getInstance();
        Merchant merchant = merchantStore.getMerchant(receiverCVR);
        return  merchant != null;
    }

    /**Removes specified barcode from the customer who has the barcode and
     * from the BarcodeStore so that the barcode is not linked to the customer anymore and therefore becomes invalid
     * @param barcode that should be removed
     */
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

    /**
     * @param barcode for which to find the connected customer
     * @return the CPR number of the customer linked to the barcode. Empty string if no customer is linked to the barcode
     */
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
