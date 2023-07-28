package com.littlepay.code.challenge.load;

public class TapDataLoaderException extends Exception {
    public TapDataLoaderException(String message, Throwable e) {
        super(message, e);
    }

    public TapDataLoaderException(String message) {
        super(message);
    }
}
