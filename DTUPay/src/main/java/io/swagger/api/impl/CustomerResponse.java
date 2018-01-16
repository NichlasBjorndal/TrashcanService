package io.swagger.api.impl;

public enum CustomerResponse {
    ALREADY_EXISTS("Customer already exist in DTUPay"),
    NO_BANK_ACCOUNT("Customer doesn't have bank account"),
    INVALID_INPUT("Invalid input");

    private String message;

    CustomerResponse(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}