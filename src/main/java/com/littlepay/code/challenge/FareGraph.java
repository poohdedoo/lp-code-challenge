package com.littlepay.code.challenge;

/**
 * Abstraction to be implemented for plugging in fare graph providers.
 */
public interface FareGraph {
    void init();

    /**
     * Returns a fare associated with a trip given its origin and destination.
     * @param origin of a given trip.
     * @param destination of a given trip.
     * @return the fare associated with the trip.
     */
    double getFare(String origin, String destination);

    /**
     * Returns the maximum fare associated with an incomplete trip.
     * @param origin of the trip.
     * @return the maximum fare associated with it.
     */
    double getMaximumFareFromOrigin(String origin);
}
