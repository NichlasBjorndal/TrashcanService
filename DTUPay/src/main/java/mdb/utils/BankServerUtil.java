package mdb.utils;

import core.FastMoneyTransaction;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;

import java.math.BigDecimal;

public class BankServerUtil {
    /**
     * @return server reference for FastMoney Bank service
     */
    public static BankService getServer() {
        BankServiceService service = new BankServiceService();
        return service.getBankServicePort();
    }

    /**
     * @param transaction model of the transaction ot be performed.
     * @throws BankServiceException_Exception Exception from FastMoney Bank service.
     */
    public static void transferMoney(FastMoneyTransaction transaction) throws BankServiceException_Exception {
        BankService bankService = BankServerUtil.getServer();

        String senderAccountId;
        String receiverAccountId;
        BigDecimal amount = transaction.getAmount();
        String message = transaction.getMessage();

        senderAccountId = bankService.getAccountByCprNumber(transaction.getSenderCpr()).getId();
        receiverAccountId = bankService.getAccountByCprNumber(transaction.getReceiverCvr()).getId();

        bankService.transferMoneyFromTo(senderAccountId, receiverAccountId, amount, message);
    }
}