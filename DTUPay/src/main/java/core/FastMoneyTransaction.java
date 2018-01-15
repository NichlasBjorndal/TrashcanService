package core;

import java.math.BigDecimal;

/**
 * Model for transactions in the FastMoney Bank.
 */
public class FastMoneyTransaction {
    private String senderCpr;
    private String receiverCvr;
    private BigDecimal amount;
    private String message;

    public FastMoneyTransaction(String senderCpr, String receiverCvr, BigDecimal amount, String message) {
        this.senderCpr = senderCpr;
        this.receiverCvr = receiverCvr;
        this.amount = amount;
        this.message = message;
    }

    /**
     * @return Sender's CPR number.
     */
    public String getSenderCpr() {
        return senderCpr;
    }

    /**
     * @return Receiver's CPR number.
     */
    public String getReceiverCvr() {
        return receiverCvr;
    }

    /**
     * @return Amount to be sent.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @return Message for transaction.
     */
    public String getMessage() {
        return message;
    }
}