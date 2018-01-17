package io.swagger.api.impl;

public enum MerchantResponse {
    ALREADY_EXISTS("Merchant already exist in DTUPay"),
    NO_BANK_ACCOUNT("Merchant doesn't have bank account"),
    INVALID_INPUT("Invalid input");

    private String message;

    MerchantResponse(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}