package com.littlepay.code.challenge;

/**
 * Abstraction to be implemented for plugging in fare graph providers.
 */
public interface FareGraph {
    void init();

    double getFare(String origin, String destination);

    double getMaximumFareFromOrigin(String origin);
}
