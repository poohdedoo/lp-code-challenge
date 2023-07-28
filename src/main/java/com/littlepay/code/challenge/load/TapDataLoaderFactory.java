package com.littlepay.code.challenge.load;

import java.nio.file.Paths;

/**
 * Hides the instantiation of tap data loader from the core business
 * logic.
 */
public class TapDataLoaderFactory {
    public static TapDataLoader getTapDataProvider() throws TapDataLoaderInitializationException {
        return new CSVBasedTapDataLoader(Paths.get(System.getProperty("user.dir"), "input"));
    }
}
