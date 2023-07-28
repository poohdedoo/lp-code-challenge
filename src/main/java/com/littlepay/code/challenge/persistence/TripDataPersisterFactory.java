package com.littlepay.code.challenge.persistence;

import java.nio.file.Paths;

/**
 * Hides the instantiation of trip data persister from the core business
 * logic.
 */
public class TripDataPersisterFactory {
    private static final String TRIP_DATA_TARGET_PATH = "trips.csv";

    public static TripDataPersister getTripDataPersister() {
        return new CSVBasedTripDataPersister(
                Paths.get(System.getProperty("user.dir"), "output", TRIP_DATA_TARGET_PATH));
    }
}
