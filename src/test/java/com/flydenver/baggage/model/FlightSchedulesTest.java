package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.DuplicateFlightException;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.flydenver.baggage.model.FlightSchedules.parse;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FlightSchedulesTest {

    public static final String FLIGHT_ID = "UA10";
    public static final String FLIGHT_GATE = "A1";
    public static final String FLIGHT_DESTINATION = "JFK";
    public static final String FLIGHT_TIME = "08:00";
    public static final String INPUT_FORMAT = "%s %s %s %s";

    private Date flightDateTime;
    FlightSchedules flightSchedules;

    @Before
    public void setUp() throws Exception {
        Set<Flight> flights = new HashSet<Flight>();
        flights.add(new Flight(FLIGHT_ID, new Node(FLIGHT_GATE), FLIGHT_DESTINATION, flightDateTime));
        flightSchedules = new FlightSchedules(flights);
        flightDateTime = new SimpleDateFormat("HH:mm").parse(FLIGHT_TIME);
    }

    @Test
    public void flightSetShouldBeImmutable() throws Exception {
        Set<Flight> flights = flightSchedules.getFlights();
        flights.clear();
        assertThat(flightSchedules.getFlights()).isNotNull().isNotEmpty();
    }

    @Test(expected = DuplicateFlightException.class)
    public void addingDuplicateFlightInformationShouldThrowException() throws Exception {
        Flight flight = new Flight(FLIGHT_ID, new Node(FLIGHT_GATE), FLIGHT_DESTINATION, flightDateTime);
        flightSchedules.addFlight(flight);
    }

    @Test
    public void shouldParseMultiLineFormattedInput() throws Exception {
        String[] multiLineFormattedInput = new String[]{
                format(INPUT_FORMAT, FLIGHT_ID, FLIGHT_GATE, FLIGHT_DESTINATION, FLIGHT_TIME),
        };
        FlightSchedules flightSchedules = parse(asList(multiLineFormattedInput));
        assertThat(flightSchedules.getFlights()).hasSize(2);
    }
}