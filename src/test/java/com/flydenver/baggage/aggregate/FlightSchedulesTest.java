package com.flydenver.baggage.aggregate;

import com.flydenver.baggage.entity.Conveyor;
import com.flydenver.baggage.entity.Flight;
import com.flydenver.baggage.entity.Node;
import com.flydenver.baggage.exception.DuplicateFlightException;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.flydenver.baggage.aggregate.FlightSchedules.parse;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FlightSchedulesTest {

    private static final String FLIGHT_ID = "UA10";
    private static final String FLIGHT_GATE = "A1";
    private static final String FLIGHT_DESTINATION = "JFK";
    private static final String FLIGHT_TIME = "08:00";
    private static final String INPUT_FORMAT = "%s %s %s %s";
    private static final String ENTRY_POINT = "A10";

    private Date flightDateTime;
    private ConveyorSystem conveyorSystem;
    private FlightSchedules flightSchedules;

    @Before
    public void setUp() throws Exception {
        Set<Conveyor> conveyors = new HashSet<>();
        conveyors.add(new Conveyor(new Node(ENTRY_POINT), new Node(FLIGHT_GATE), 10));
        conveyorSystem = new ConveyorSystem(conveyors);

        Set<Flight> flights = new HashSet<>();
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
        FlightSchedules flightSchedules = parse(asList(multiLineFormattedInput), conveyorSystem);
        assertThat(flightSchedules.getFlights()).hasSize(2);
    }
}