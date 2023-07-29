package com.littlepay.code.challenge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TripBuilder {
    private final List<Tap> taps;
    private static final Logger LOG = LogManager.getLogger(TripBuilder.class);

    public TripBuilder(final List<Tap> taps) {
        if (taps == null || taps.isEmpty()) {
            throw new IllegalArgumentException("Tap data is null or empty.");
        }
        this.taps = taps;
    }

    public List<Trip> buildTrips() {
        List<Trip> trips = new ArrayList<>();

        Map<String, List<Tap>> tapsByPan = this.taps.stream()
                .collect(Collectors.groupingBy(Tap::getPan));

        //FIXME: Do the following with a worker pool.
        for (List<Tap> tapsOfPan : tapsByPan.values()) {
            List<Tap> sortedTapsOfPan = sortTapsOfPan(tapsOfPan);
            buildTripsForPan(trips, sortedTapsOfPan);
        }

        return trips.stream()
                .sorted(Comparator.comparing(Trip::getStartTime))
                .collect(Collectors.toList());
    }

    private List<Tap> sortTapsOfPan(List<Tap> tapsOfPan) {
        return tapsOfPan.stream()
                .sorted(Comparator.comparing(Tap::getDateTime))
                .collect(Collectors.toList());
    }

    private void buildTripsForPan(List<Trip> trips, List<Tap> sortedTapsOfPan) {
        for (int i = 0; i < sortedTapsOfPan.size(); i++) {
            Tap tap1 = sortedTapsOfPan.get(i);
            Tap tap2 = null;
            if (i != sortedTapsOfPan.size() - 1) {
                tap2 = sortedTapsOfPan.get(i + 1);
            }

            // Explicitly handles scenario where either the last trip of a passenger was incomplete or invalid.
            if (tap2 == null) {
                switch (tap1.getTapType()) {
                    case ON -> trips.add(TripBuilderHelper.buildIncompleteTrip(tap1));
                    case OFF -> {
                        // Ignored with a warning, as this could indicate a data error.
                        LOG.warn("Tap OFF was detected without a corresponding tap ON.");
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Details of the tap OFF event without a corresponding tap ON => " + tap1);
                        }
                    }
                }
                continue;
            }

            switch (tap1.getTapType()) {
                case ON -> {
                    if (Tap.Type.OFF.equals(tap2.getTapType()) && !tap1.isSameStopAs(tap2)) {
                        trips.add(TripBuilderHelper.buildCompletedTrip(tap1, tap2));
                        i++;
                    } else if (Tap.Type.ON.equals(tap2.getTapType()) && !tap1.isSameStopAs(tap2)) {
                        trips.add(TripBuilderHelper.buildIncompleteTrip(tap1));
                    } else if (Tap.Type.OFF.equals(tap2.getTapType()) && tap1.isSameStopAs(tap2)) {
                        trips.add(TripBuilderHelper.buildCancelledTrip(tap1));
                    }
                }
                case OFF -> {
                    // Ignored with a warning, as this could indicate a data error.
                    LOG.warn("Tap OFF was detected without a corresponding tap ON.");
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Details of the tap OFF event without a corresponding tap ON => " + tap1);
                    }
                }
            }
        }
    }

    private static class TripBuilderHelper {
        private static final FareGraph fareGraph;

        static {
            fareGraph = new FareGraphImpl();
            fareGraph.init();
        }

        private static Trip buildIncompleteTrip(Tap tap) {
            double fare = calculateFee(tap.getStopId(), null, Trip.Status.INCOMPLETE);
            return new TripBuilderHelper().buildTrip(tap, Trip.DESTINATION_UNKNOWN, fare, Trip.Status.INCOMPLETE);
        }

        private static Trip buildCancelledTrip(Tap tap) {
            double fare = calculateFee(tap.getStopId(), tap.getStopId(), Trip.Status.CANCELLED);
            return new TripBuilderHelper().buildTrip(tap, Trip.DESTINATION_NA, fare, Trip.Status.CANCELLED);
        }

        private static Trip buildCompletedTrip(Tap tap1, Tap tap2) {
            String origin = tap1.getStopId();
            String destination = tap2.getStopId();
            double fee = calculateFee(origin, destination, Trip.Status.COMPLETED);
            return new TripBuilderHelper().buildTrip(tap1, tap2.getDateTime(), destination, fee);
        }

        private Trip buildTrip(Tap tap, String destination, double fee, Trip.Status status) {
            Trip trip = new Trip();
            trip.setStartTime(tap.getDateTime());
            trip.setEndTime(tap.getDateTime()); // Sets the end time to be the same as start time
            trip.setOrigin(tap.getStopId());
            trip.setCompanyId(tap.getCompanyId());
            trip.setBusId(tap.getBusId());
            trip.setPan(tap.getPan());
            trip.setDestination(destination);
            trip.setFee(fee);
            trip.setStatus(status);
            return trip;
        }

        private Trip buildTrip(Tap tap1, Date endTime, String destination, double fee) {
            Trip trip = buildTrip(tap1, destination, fee, Trip.Status.COMPLETED);
            trip.setEndTime(endTime);
            trip.setDestination(destination);
            return trip;
        }

        private static double calculateFee(String origin, String destination, Trip.Status status) {
            double fare = 0;
            switch (status) {
                case COMPLETED -> fare = fareGraph.getFare(origin, destination);
                case INCOMPLETE -> fare = fareGraph.getMaximumFareFromOrigin(origin);
                case CANCELLED -> fare = 0;
            }
            return fare;
        }
    }
}
