package com.unibooking.backend.Exception;

public class ProviderAlreadyExistsException extends RuntimeException {
    public ProviderAlreadyExistsException(String message) {
        super(message);
    }
}
