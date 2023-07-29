package com.littlepay.code.challenge;

/**
 * This exception is thrown in scenario where an unexpected error occurs
 * while handling overall trip processing within the application.
 */
public class TripProcessorException extends Exception {
    public TripProcessorException(String message, Throwable e) {
        super(message, e);
    }
}
