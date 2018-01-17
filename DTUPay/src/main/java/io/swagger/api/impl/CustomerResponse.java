package io.swagger.api.impl;

/**
 * An enum of response messages to be used when dealing with customers in the system
 */
public enum CustomerResponse {
    ALREADY_EXISTS("Customer already exist in DTUPay"),
    NO_BANK_ACCOUNT("Customer doesn't have bank account"),
    INVALID_INPUT("Invalid input");

    private String message;

    /**
     * @param message specifies the message to be tied with a new enum value
     */
    CustomerResponse(String message) {
        this.message = message;
    }

    /**
     * @return the message associated the enum value calling the method
     */
    public String getValue() {
        return message;
    }
}