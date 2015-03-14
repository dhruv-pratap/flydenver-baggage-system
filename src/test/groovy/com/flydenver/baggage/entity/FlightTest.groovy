package com.flydenver.baggage.entity

import com.flydenver.baggage.aggregate.ConveyorSystem
import com.flydenver.baggage.exception.FlightParseException
import org.junit.Before
import org.junit.Test

import java.text.SimpleDateFormat

import static com.flydenver.baggage.entity.Flight.parse
import static java.lang.String.format
import static org.assertj.core.api.Assertions.assertThat

class FlightTest {

    def FLIGHT_ID = "UA10"
    def FLIGHT_GATE = "A1"
    def FLIGHT_DESTINATION = "JFK"
    def FLIGHT_TIME = "08:00"
    def ENTRY_POINT = "A10"

    def conveyorSystem

    @Before
    public void setUp() throws Exception {
        conveyorSystem = new ConveyorSystem([new Conveyor(new Node(ENTRY_POINT), new Node(FLIGHT_GATE), 10)] as Set)
    }

    @Test
    public void shouldParseFormattedStringIntoFlightObject() throws Exception {
        def flight = parse(format("%s %s %s %s", FLIGHT_ID, FLIGHT_GATE, FLIGHT_DESTINATION, FLIGHT_TIME), conveyorSystem)
        assertThat(flight.getId()).isEqualTo(FLIGHT_ID)
        assertThat(flight.getGate().getId()).isEqualTo(FLIGHT_GATE)
        assertThat(flight.getDestination()).isEqualTo(FLIGHT_DESTINATION)
        assertThat(flight.getTime()).isEqualTo(new SimpleDateFormat("HH:mm").parse(FLIGHT_TIME))
    }

    @Test(expected = FlightParseException.class)
    public void shouldThrowParseExceptionForMoreNumberOfTokens() throws Exception {
        parse(format("%s %s %s %s 10", FLIGHT_ID, FLIGHT_GATE, FLIGHT_DESTINATION, FLIGHT_TIME), conveyorSystem)
    }

    @Test(expected = FlightParseException.class)
    public void shouldThrowParseExceptionForLessNumberOfTokens() throws Exception {
        parse(format("%s %s %s", FLIGHT_ID, FLIGHT_GATE, FLIGHT_DESTINATION), conveyorSystem)
    }

    @Test(expected = FlightParseException.class)
    public void shouldThrowParseExceptionForNullInput() throws Exception {
        parse(null, conveyorSystem)
    }
}