package com.littlepay.code.challenge.load;

public class TapDataLoaderInitializationException extends Exception {
    public TapDataLoaderInitializationException(String message, Throwable e) {
        super(message, e);
    }

    public TapDataLoaderInitializationException(String message) {
        super(message);
    }
}
