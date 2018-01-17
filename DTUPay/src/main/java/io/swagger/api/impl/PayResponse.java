package io.swagger.api.impl;

public enum PayResponse {
    SUCCESSFUL_PAYMENT("New transaction completed"),
    INVALID_INPUT("Invalid input"),
    INVALID_BARCODE("Barcode is invalid"),
    UNEXPECTED("Unexpected"),
    INVALID_MERCHANT("Merchant does not exist in DTUPay");

    private String message;

    PayResponse(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}
