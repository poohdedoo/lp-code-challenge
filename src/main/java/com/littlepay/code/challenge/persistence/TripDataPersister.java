package com.littlepay.code.challenge.persistence;

import com.littlepay.code.challenge.Trip;

import java.util.List;

/**
 * Abstraction to be implemented for trip data persistence atop
 * any arbitrary data source.
 */
public interface TripDataPersister {
    /**
     * Persists trip data in the underlying data source.
     * @param trips a valid list of trip data.
     * @throws TapDataPersistenceException in case an unexpected error occurs.
     */
    void persist(List<Trip> trips) throws TapDataPersistenceException;
}
