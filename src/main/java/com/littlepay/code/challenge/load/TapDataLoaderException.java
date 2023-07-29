package com.littlepay.code.challenge.load;

/**
 * This exception is thrown in scenarios where an error occurs while loading tap data
 * from an underlying tap data loader.
 */
public class TapDataLoaderException extends Exception {
    public TapDataLoaderException(String message, Throwable e) {
        super(message, e);
    }
}
