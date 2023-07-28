package com.littlepay.code.challenge;

public class TripProcessorException extends Exception {
    public TripProcessorException(String message, Throwable e) {
        super(message, e);
    }

    public TripProcessorException(String message) {
        super(message);
    }
}
