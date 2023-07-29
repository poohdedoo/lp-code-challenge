package com.littlepay.code.challenge.persistence;

/**
 * This exception is thrown in scenarios where an error occurs while initializing
 * a trip data persister.
 */
public class TripDataPersisterInitializationException extends Exception {
    public TripDataPersisterInitializationException(String message, Throwable e) {
        super(message, e);
    }
}
