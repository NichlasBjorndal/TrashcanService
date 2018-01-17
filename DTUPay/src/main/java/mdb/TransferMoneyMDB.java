package mdb;

import core.FastMoneyTransaction;
import dtu.ws.fastmoney.BankServiceException_Exception;
import core.utils.BankServerUtil;
import core.utils.GsonWrapper;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(name = "FastMoneyBankTransactionMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/FastMoneyBankTransactionQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})

/**
 * Message driven bean for transactions in the FastMoney Bank. Listens to the FastMoneyBankTransactionQueue.
 */
public class TransferMoneyMDB extends BaseMDB {

    /**
     * @param receivedText JSON object of a FastMoneyTransaction to be performed.
     * @return Transaction message if successful, otherwise exception thrown by bank.
     */
    @Override
    protected String processMessage(String receivedText) {
        FastMoneyTransaction transaction = (FastMoneyTransaction) GsonWrapper.fromJson(receivedText, FastMoneyTransaction.class);

        try {
            BankServerUtil.transferMoney(transaction);
        } catch (BankServiceException_Exception e) {
            return (GsonWrapper.toJson(e.getMessage()));
        }

        return GsonWrapper.toJson(transaction.getMessage());
    }
}