package com.littlepay.code.challenge.persistence;

public class TripDataPersistenceException extends Exception {
    public TripDataPersistenceException(String message, Throwable e) {
        super(message, e);
    }

    public TripDataPersistenceException(String message) {
        super(message);
    }
}
