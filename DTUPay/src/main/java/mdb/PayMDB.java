package mdb;

import core.barcode.Model.Barcode;
import core.persistence.BarcodeStore;
import core.persistence.CustomerStore;
import core.persistence.MerchantStore;
import core.user.Customer;
import core.user.Merchant;
import core.utils.BankServerUtil;
import core.utils.GsonWrapper;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.swagger.api.impl.PayResponse;
import io.swagger.model.Transaction;


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
        boolean validMerchant = validateMerchant(transaction.getReceiverCVR());

        BankService server = BankServerUtil.getServer();
        String response;

        if (customerCPR.equals("")) {
            response = PayResponse.INVALID_BARCODE.getValue();
        } else if (validMerchant) {
            try {
                String senderAccountId = server.getAccountByCprNumber(customerCPR).getId();
                String receiverAccountId = server.getAccountByCprNumber(transaction.getReceiverCVR()).getId();
                server.transferMoneyFromTo(senderAccountId, receiverAccountId, transaction.getAmount(),"Successful transaction");
                response = PayResponse.SUCCESSFUL_PAYMENT.getValue();
                removeBarcode(transaction.getBarcode());
            } catch (BankServiceException_Exception e) {

                if (e.getMessage().equals("Debtor balance will be negative")) {
                    response = PayResponse.INVALID_INPUT.getValue();
                } else {
                    response = e.getMessage();
                }
            }
        } else {
            response = PayResponse.INVALID_MERCHANT.getValue();
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
