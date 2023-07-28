package com.littlepay.code.challenge.persistence;

public class TapDataPersistenceException extends Exception {
    public TapDataPersistenceException(String message, Throwable e) {
        super(message, e);
    }

    public TapDataPersistenceException(String message) {
        super(message);
    }
}
