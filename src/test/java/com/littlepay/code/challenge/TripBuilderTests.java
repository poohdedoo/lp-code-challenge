package com.littlepay.code.challenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TripBuilderTests {
    private static Stream<List<Tap>> tapsBetweenS1S2() throws ParseException {
        List<Tap> tapsS1S2 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        tapsS1S2.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        tapsS1S2.add(new Tap(2, sdf.parse("22-01-2023 13:00:05"), Tap.Type.OFF, "Stop2", "Company1", "Bus37",
                "5500005555555559"));

        List<Tap> tapsS2S1 = new ArrayList<>();
        tapsS2S1.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop2", "Company1", "Bus37",
                "5500005555555559"));
        tapsS2S1.add(new Tap(2, sdf.parse("22-01-2023 13:00:05"), Tap.Type.OFF, "Stop1", "Company1", "Bus37",
                "5500005555555559"));

        return Stream.of(tapsS1S2, tapsS2S1);
    }

    private static Stream<List<Tap>> tapsBetweenS1S3() throws ParseException {
        List<Tap> tapsS1S3 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        tapsS1S3.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        tapsS1S3.add(new Tap(2, sdf.parse("22-01-2023 13:00:05"), Tap.Type.OFF, "Stop3", "Company1", "Bus37",
                "5500005555555559"));

        List<Tap> tapsS3S1 = new ArrayList<>();
        tapsS3S1.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop3", "Company1", "Bus37",
                "5500005555555559"));
        tapsS3S1.add(new Tap(2, sdf.parse("22-01-2023 13:00:05"), Tap.Type.OFF, "Stop1", "Company1", "Bus37",
                "5500005555555559"));

        return Stream.of(tapsS1S3, tapsS3S1);
    }

    private static Stream<List<Tap>> tapsBetweenS2S3() throws ParseException {
        List<Tap> tapsS2S3 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        tapsS2S3.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop2", "Company1", "Bus37",
                "5500005555555559"));
        tapsS2S3.add(new Tap(2, sdf.parse("22-01-2023 13:00:05"), Tap.Type.OFF, "Stop3", "Company1", "Bus37",
                "5500005555555559"));

        List<Tap> tapsS3S2 = new ArrayList<>();
        tapsS3S2.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop3", "Company1", "Bus37",
                "5500005555555559"));
        tapsS3S2.add(new Tap(2, sdf.parse("22-01-2023 13:00:05"), Tap.Type.OFF, "Stop2", "Company1", "Bus37",
                "5500005555555559"));

        return Stream.of(tapsS2S3, tapsS3S2);
    }

    @Test
    public void whenGivenNullTapData_throwsIllegalParamException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TripBuilder(new ArrayList<>(0));
        });
    }

    @Test
    public void whenGivenEmptyTapData_throwsIllegalParamException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TripBuilder(new ArrayList<>(0));
        });
    }

    @Test
    public void whenGivenValidTapData_returnsValidTripData() throws ParseException {
        List<Tap> taps = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        taps.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        taps.add(new Tap(2, sdf.parse("22-01-2023 13:00:05"), Tap.Type.OFF, "Stop2", "Company1", "Bus37",
                "5500005555555559"));
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(1, trips.size());
    }

    @ParameterizedTest
    @MethodSource("tapsBetweenS1S2")
    public void whenUserTapsOnAndOffBetweenS1S2_returnsValidTripFee(List<Tap> taps) throws ParseException {
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(1, trips.size());

        Trip trip = trips.get(0);
        assertEquals(3.25, trip.getFee());
        assertEquals(Trip.Status.COMPLETED, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());
    }

    @ParameterizedTest
    @MethodSource("tapsBetweenS1S3")
    public void whenUserTapsOnAndOffBetweenS1S3_returnsValidTripFee(List<Tap> taps) throws ParseException {
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(1, trips.size());

        Trip trip = trips.get(0);
        assertEquals(7.30, trip.getFee());
        assertEquals(Trip.Status.COMPLETED, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());
    }

    @ParameterizedTest
    @MethodSource("tapsBetweenS2S3")
    public void whenUserTapsOnAndOffBetweenS2S3_returnsValidTripFee(List<Tap> taps) throws ParseException {
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(1, trips.size());

        Trip trip = trips.get(0);
        assertEquals(5.50, trip.getFee());
        assertEquals(Trip.Status.COMPLETED, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());
    }

    @Test
    public void whenUserTapsOnMultipleTimesWithoutTappingOff_returnsMaximumTripFee() throws ParseException {
        List<Tap> taps = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        taps.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        taps.add(new Tap(2, sdf.parse("22-01-2023 14:00:05"), Tap.Type.ON, "Stop2", "Company1", "Bus38",
                "5500005555555559"));
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(2, trips.size());

        Trip trip = trips.get(0);
        assertEquals(7.30, trip.getFee());
        assertEquals(Trip.Status.INCOMPLETE, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());
        assertEquals(Trip.DESTINATION_UNKNOWN, trip.getDestination());

        trip = trips.get(1);
        assertEquals(5.50, trip.getFee());
        assertEquals(Trip.Status.INCOMPLETE, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());
        assertEquals(Trip.DESTINATION_UNKNOWN, trip.getDestination());
    }

    @Test
    public void whenUserTapsOnFromS1WithoutTappingOffInLastTrip_returnsMaximumTripFee() throws ParseException {
        List<Tap> taps = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        taps.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(1, trips.size());

        Trip trip = trips.get(0);
        assertEquals(7.30, trip.getFee());
        assertEquals(Trip.Status.INCOMPLETE, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());
        assertEquals(Trip.DESTINATION_UNKNOWN, trip.getDestination());
    }

    @Test
    public void whenUserTapsOnAndOffAtS1_returnsZeroFare() throws ParseException {
        List<Tap> taps = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        taps.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        taps.add(new Tap(2, sdf.parse("22-01-2023 13:00:02"), Tap.Type.OFF, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(1, trips.size());

        Trip trip = trips.get(0);
        assertEquals(0, trip.getFee());
        assertEquals(Trip.Status.CANCELLED, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());
        assertEquals(Trip.DESTINATION_NA, trip.getDestination());
    }

    @Test
    public void whenUnOrderedCompletedTapDataOfMultipleUsersProvided_returnsValidTripData() throws ParseException {
        List<Tap> taps = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        taps.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        taps.add(new Tap(2, sdf.parse("22-01-2023 13:02:00"), Tap.Type.ON, "Stop1", "Company1", "Bus38",
                "4111111111111111"));
        taps.add(new Tap(3, sdf.parse("22-01-2023 13:05:00"), Tap.Type.OFF, "Stop2", "Company1", "Bus37",
                "5500005555555559"));
        taps.add(new Tap(4, sdf.parse("22-01-2023 14:10:00"), Tap.Type.OFF, "Stop3", "Company1", "Bus38",
                "4111111111111111"));
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(2, trips.size());

        Trip trip = trips.get(0);
        assertEquals(3.25, trip.getFee());
        assertEquals(Trip.Status.COMPLETED, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());

        trip = trips.get(1);
        assertEquals(7.30, trip.getFee());
        assertEquals(Trip.Status.COMPLETED, trip.getStatus());
        assertEquals("4111111111111111", trip.getPan());
    }

    @Test
    public void whenOrderedMixedTapDataOfMultipleUsersProvided_returnsValidTripData() throws ParseException {
        List<Tap> taps = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        taps.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37", "5500005555555559"));
        taps.add(new Tap(2, sdf.parse("22-01-2023 13:05:00"), Tap.Type.OFF, "Stop2", "Company1", "Bus37", "5500005555555559"));
        taps.add(new Tap(3, sdf.parse("22-01-2023 09:20:00"), Tap.Type.ON, "Stop3", "Company1", "Bus36", "4111111111111111"));
        taps.add(new Tap(4, sdf.parse("23-01-2023 08:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37", "4111111111111111"));
        taps.add(new Tap(5, sdf.parse("23-01-2023 08:02:00"), Tap.Type.OFF, "Stop1", "Company1", "Bus37", "4111111111111111"));

        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(3, trips.size());

        Trip trip = trips.get(0);
        assertEquals(7.30, trip.getFee());
        assertEquals(Trip.Status.INCOMPLETE, trip.getStatus());
        assertEquals("4111111111111111", trip.getPan());

        trip = trips.get(1);
        assertEquals(3.25, trip.getFee());
        assertEquals(Trip.Status.COMPLETED, trip.getStatus());
        assertEquals("5500005555555559", trip.getPan());

        trip = trips.get(2);
        assertEquals(0, trip.getFee());
        assertEquals(Trip.Status.CANCELLED, trip.getStatus());
        assertEquals("4111111111111111", trip.getPan());
    }

    @Test
    public void whenMixedTapDataOfMultipleUsersProvided_returnsTripDataOrderedByStartDateTime() throws ParseException {
        List<Tap> taps = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        taps.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37", "5500005555555559"));
        taps.add(new Tap(2, sdf.parse("22-01-2023 13:05:00"), Tap.Type.OFF, "Stop2", "Company1", "Bus37", "5500005555555559"));
        taps.add(new Tap(3, sdf.parse("22-01-2023 09:20:00"), Tap.Type.ON, "Stop3", "Company1", "Bus36", "4111111111111111"));
        taps.add(new Tap(4, sdf.parse("23-01-2023 08:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37", "4111111111111111"));
        taps.add(new Tap(5, sdf.parse("23-01-2023 08:02:00"), Tap.Type.OFF, "Stop1", "Company1", "Bus37", "4111111111111111"));

        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertFalse(trips.isEmpty(), "Trip data returned is empty. Expected a non-empty response.");
        assertEquals(3, trips.size());

        Iterator<Trip> iterator = trips.iterator();
        if (iterator.hasNext()) {
            Date prevDate = iterator.next().getStartTime();
            while (iterator.hasNext()) {
                Date currDate = iterator.next().getStartTime();
                assertTrue(currDate.after(prevDate),
                        "Trips aren't ordered in a way that the most recent trip always appears last.");
                prevDate = currDate;
            }
        }
    }

    @Test
    public void whenTapOffWithoutTapOn_noTripDataGenerated() throws ParseException {
        List<Tap> taps = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        taps.add(new Tap(1, sdf.parse("22-01-2023 13:00:00"), Tap.Type.OFF, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        TripBuilder tripBuilder = new TripBuilder(taps);

        List<Trip> trips = tripBuilder.buildTrips();
        assertNotNull(trips, "Trip data returned is null. Expected a non-null response.");
        assertTrue(trips.isEmpty(), "Trip data returned is non-empty. Expected an empty response.");
    }


}
