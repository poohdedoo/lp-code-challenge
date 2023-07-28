package com.littlepay.code.challenge.persistence;

import java.nio.file.Paths;

/**
 * Hides the instantiation of trip data persister from the core business
 * logic.
 */
public class TripDataPersisterFactory {
    public static TripDataPersister getTripDataPersister() throws TripDataPersisterInitializationException {
        return new CSVBasedTripDataPersister(Paths.get(System.getProperty("user.dir"), "output"));
    }
}
