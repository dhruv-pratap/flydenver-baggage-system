package com.flydenver.baggage.aggregate

import com.flydenver.baggage.entity.Conveyor
import com.flydenver.baggage.entity.Flight
import com.flydenver.baggage.entity.Node
import com.flydenver.baggage.exception.DuplicateFlightException
import com.flydenver.baggage.exception.UnknownFlightException
import org.junit.Before
import org.junit.Test

import java.text.SimpleDateFormat

import static com.flydenver.baggage.aggregate.FlightSchedules.parse
import static java.lang.String.format
import static org.assertj.core.api.Assertions.assertThat

public class FlightSchedulesTest {

    def FLIGHT_ID = "UA10"
    def FLIGHT_GATE = "A1"
    def FLIGHT_DESTINATION = "JFK"
    def FLIGHT_TIME = "08:00"
    def INPUT_FORMAT = "%s %s %s %s"
    def ENTRY_POINT = "A10"

    def flightDateTime
    def conveyorSystem
    def flightSchedules

    @Before
    public void setUp() throws Exception {
        conveyorSystem = new ConveyorSystem([new Conveyor(new Node(ENTRY_POINT), new Node(FLIGHT_GATE), 10)] as Set)
        flightSchedules = new FlightSchedules([new Flight(FLIGHT_ID, new Node(FLIGHT_GATE), FLIGHT_DESTINATION, flightDateTime)] as Set)
        flightDateTime = new SimpleDateFormat("HH:mm").parse(FLIGHT_TIME)
    }

    @Test
    public void shouldHaveImmutableFlightSet() throws Exception {
        flightSchedules.getFlights().clear()
        assertThat(flightSchedules.getFlights()).isNotNull().isNotEmpty()
    }

    @Test(expected = DuplicateFlightException.class)
    public void shouldThrowExceptionWhenDuplicateFlightInformationIsAdded() throws Exception {
        def flight = new Flight(FLIGHT_ID, new Node(FLIGHT_GATE), FLIGHT_DESTINATION, flightDateTime)
        flightSchedules.addFlight(flight)
    }

    @Test
    public void shouldParseMultiLineFormattedInput() throws Exception {
        def multiLineFormattedInput = [format(INPUT_FORMAT, FLIGHT_ID, FLIGHT_GATE, FLIGHT_DESTINATION, FLIGHT_TIME)]
        def flightSchedules = parse(multiLineFormattedInput, conveyorSystem)
        assertThat(flightSchedules.getFlights()).hasSize(2)
    }

    @Test(expected = UnknownFlightException.class)
    public void shouldThrowExceptionWhenCannotFindFlight() throws Exception {
        flightSchedules.findFlightById("X")
    }

    @Test
    public void shouldReturnAnExistingFlightWhenExists() throws Exception {
        assertThat(flightSchedules.findFlightById("UA10")).isNotNull()
    }
}