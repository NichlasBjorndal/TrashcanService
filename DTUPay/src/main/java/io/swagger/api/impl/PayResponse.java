package io.swagger.api.impl;

public enum PayResponse {
    SUCCESSFUL_PAYMENT("New transaction completed"),
    INVALID_BARCODE("Barcode is invalid"),
    NO_BANK_ACCOUNT("Bank account does not exist"),
    INVALID_MERCHANT("Merchant does not exist in DTUPay"),
    NOT_ENOUGH_FUNDS("Not enough funds to make transfer"),
    SERVER_ERROR("Server error");

    private String message;

    PayResponse(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}
