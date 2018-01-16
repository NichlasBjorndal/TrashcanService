package io.swagger.api.impl;

public enum PayResponse {
    SUCCESSFUL_PAYMENT("New transaction completed"),
    INVALID_INPUT("Invalid input");

    private String message;

    PayResponse(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}
