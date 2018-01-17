package core.utils;

import core.FastMoneyTransaction;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;

import java.math.BigDecimal;

/**
 * @author Mathias Thomsen
 *  A utility class for quick access to the necessary FastMoney Bank tools.
 */
public class BankServerUtil {
    /**
     * Return server reference for FastMoney Bank service.
     *
     * @return server reference for FastMoney Bank service.
     */
    public static BankService getServer() {
        BankServiceService service = new BankServiceService();
        return service.getBankServicePort();
    }

    /**
     * Performs a transaction through the FastMoney Bank using the given transaction model.
     *
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

    /**
     * Returns <code>true</code> if the FastMoney Bank contains a user with the given CPR.
     *
     * @param cpr CPR/CVR number of some client or merchant.
     * @return <code>true</code> if the FastMoney Bank contains a user with the given CPR;
     *         <code>false</code> otherwise
     */
    public static boolean checkIfBankAccountExistsById(String cpr) {
        BankService server = BankServerUtil.getServer();

        Account accountByIdentifier = null;

        try {
            accountByIdentifier = server.getAccountByCprNumber(cpr);
        } catch (BankServiceException_Exception e) {
        }

        return accountByIdentifier != null;
    }
}