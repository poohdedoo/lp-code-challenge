package com.littlepay.code.challenge.load;

import java.nio.file.Paths;

/**
 * Hides the instantiation of tap data loader from the core business
 * logic.
 */
public class TapDataLoaderFactory {
    private static final String TAP_DATA_SOURCE_PATH = "taps.csv";

    public static TapDataLoader getTapDataProvider() {
        return new CSVBasedTapDataLoader(
                Paths.get(System.getProperty("user.dir"), "input", TAP_DATA_SOURCE_PATH));
    }
}
