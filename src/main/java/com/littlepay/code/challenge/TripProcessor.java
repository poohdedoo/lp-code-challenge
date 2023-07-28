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
        TripProcessor tripProcessor = new TripProcessor();
        try {
            tripProcessor.processTrips();
        } catch (TripProcessorException e) {
            LOG.error("Error occurred while generating trip info.", e);
            throw e;
        }
    }

    public void processTrips() throws TripProcessorException {
        TapDataLoader tapDataProvider = TapDataLoaderFactory.getTapDataProvider();
        List<Tap> taps;
        try {
            taps = tapDataProvider.getTaps();
        } catch (TapDataLoaderException e) {
            String message = "Error occurred while fetching tap data.";
            LOG.error(message, e);
            throw new TripProcessorException(message, e);
        }

        List<Trip> trips = TripProcessorUtil.generateTrips(taps);

        TripDataPersister tripDataPersister = TripDataPersisterFactory.getTripDataPersister();
        try {
            tripDataPersister.persist(trips);
        } catch (TapDataPersistenceException e) {
            String message = "Error occurred while persisting trip data.";
            LOG.error(message, e);
            throw new TripProcessorException(message, e);
        }
    }
}