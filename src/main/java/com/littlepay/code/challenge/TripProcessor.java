package com.littlepay.code.challenge;

import com.littlepay.code.challenge.load.TapDataLoader;
import com.littlepay.code.challenge.load.TapDataLoaderException;
import com.littlepay.code.challenge.load.TapDataLoaderFactory;
import com.littlepay.code.challenge.persistence.TapDataPersistenceException;
import com.littlepay.code.challenge.persistence.TripDataPersister;
import com.littlepay.code.challenge.persistence.TripDataPersisterFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TripProcessor {
    private static final Logger LOG = LogManager.getLogger(TripProcessor.class);

    public static void main(String[] args) throws TripProcessorException {
        LOG.info("Beginning to run the trip data generation application.");

        TripProcessor tripProcessor = new TripProcessor();
        try {
            tripProcessor.processTrips();
        } catch (TripProcessorException e) {
            LOG.error("Error occurred while generating trip data.", e);
            throw e;
        }

        LOG.info("Trip data generation application has successfully concluded.");
    }

    public void processTrips() throws TripProcessorException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Beginning to load tap data.");
        }
        TapDataLoader tapDataProvider = TapDataLoaderFactory.getTapDataProvider();
        List<Tap> taps;
        try {
            taps = tapDataProvider.getTaps();
        } catch (TapDataLoaderException e) {
            String message = "Error occurred while fetching tap data.";
            LOG.error(message, e);
            throw new TripProcessorException(message, e);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tap data loaded successfully.");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Beginning to generate trip data from taps.");
        }
        TripBuilder tripBuilder = new TripBuilder(taps);
        List<Trip> trips = tripBuilder.buildTrips();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Trip data from taps generated successfully.");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Beginning to persist trip data.");
        }
        TripDataPersister tripDataPersister = TripDataPersisterFactory.getTripDataPersister();
        try {
            tripDataPersister.persist(trips);
        } catch (TapDataPersistenceException e) {
            String message = "Error occurred while persisting trip data.";
            LOG.error(message, e);
            throw new TripProcessorException(message, e);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Trip data persisted successfully.");
        }
    }
}
