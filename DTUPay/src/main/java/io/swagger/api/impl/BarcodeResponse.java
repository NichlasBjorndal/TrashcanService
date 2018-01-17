package io.swagger.api.impl;

public enum BarcodeResponse {
    NO_USER("User with UUID doesn't exist in DTUPay"),
    INVALID_INPUT("Invalid input");

    private String message;

    BarcodeResponse(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}
