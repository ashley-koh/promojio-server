package com.ashleykoh.promojioserver.exceptions;

public class ServerRuntimeException extends RuntimeException {

    private final String label;
    public ServerRuntimeException(String label, String message) {
        super(message);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
