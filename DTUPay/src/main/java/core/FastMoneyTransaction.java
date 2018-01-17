package core;

import java.math.BigDecimal;

/**
 * @author Gustav Madslund
 * A model which represents a transaction request send to the FastMoney Bank.
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
     * @return Sender's CPR number ({@link FastMoneyTransaction#senderCpr}).
     */
    public String getSenderCpr() {
        return senderCpr;
    }

    /**
     * @return Receiver's CPR number ({@link FastMoneyTransaction#receiverCvr}).
     */
    public String getReceiverCvr() {
        return receiverCvr;
    }

    /**
     * @return Amount to be sent ({@link FastMoneyTransaction#amount}).
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @return Message for transaction ({@link FastMoneyTransaction#message}).
     */
    public String getMessage() {
        return message;
    }
}