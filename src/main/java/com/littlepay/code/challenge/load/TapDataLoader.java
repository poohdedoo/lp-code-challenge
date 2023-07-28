package com.littlepay.code.challenge.load;

import com.littlepay.code.challenge.Tap;

import java.util.List;

/**
 * Abstraction to be implemented for tap data loading from
 * any arbitrary data source.
 */
public interface TapDataLoader {
    /**
     * Loads tap data from the underlying data source.
     * @return a list of tap data.
     * @throws TapDataLoaderException in case an unexpected error occurs.
     */
    List<Tap> getTaps() throws TapDataLoaderException;
}
