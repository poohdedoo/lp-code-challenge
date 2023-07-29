package com.littlepay.code.challenge.load;

/**
 * This exception is thrown in scenarios where an error occurs while initializing
 * a tap data loader.
 */
public class TapDataLoaderInitializationException extends Exception {
    public TapDataLoaderInitializationException(String message, Throwable e) {
        super(message, e);
    }

    public TapDataLoaderInitializationException(String message) {
        super(message);
    }
}
