package mdb;

import core.FastMoneyTransaction;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import mdb.utils.BankserverUtil;
import mdb.utils.GsonWrapper;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import java.math.BigDecimal;

@MessageDriven(name = "FastMoneyBankTransactionMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/FastMoneyBankTransactionQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})

/**
 * Message driven bean for transactions in the FastMoney Bank.
 */
public class TransferMoneyMDB extends BaseMDB {

    @Override
    protected String processMessage(String receivedText) {
        FastMoneyTransaction transaction = (FastMoneyTransaction) GsonWrapper.fromJson(receivedText, FastMoneyTransaction.class);

        BankService bankService = BankserverUtil.GetServer();
        String senderAccountId;
        String receiverAccountId;
        BigDecimal amount = transaction.getAmount();
        String message = transaction.getMessage();

        try {
            senderAccountId = bankService.getAccountByCprNumber(transaction.getSenderCpr()).getId();
            receiverAccountId = bankService.getAccountByCprNumber(transaction.getReceiverCvr()).getId();

            bankService.transferMoneyFromTo(senderAccountId, receiverAccountId, amount, message);
        } catch (BankServiceException_Exception e) {
            return (GsonWrapper.toJson(e.getMessage()));
        }
        return GsonWrapper.toJson(message);
    }
}
