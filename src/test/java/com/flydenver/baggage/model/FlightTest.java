package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.FlightParseException;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static com.flydenver.baggage.model.Flight.parse;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class FlightTest {

    public static final String FLIGHT_ID = "UA10";
    public static final String FLIGHT_GATE = "A1";
    public static final String FLIGHT_DESTINATION = "JFK";
    public static final String FLIGHT_TIME = "08:00";

    @Test
    public void shouldParseFormattedStringIntoFlightObject() throws Exception {
        Flight flight = parse(format("%s %s %s %s", FLIGHT_ID, FLIGHT_GATE, FLIGHT_DESTINATION, FLIGHT_TIME));
        assertThat(flight.getId()).isEqualTo(FLIGHT_ID);
        assertThat(flight.getGate().getId()).isEqualTo(FLIGHT_GATE);
        assertThat(flight.getDestination()).isEqualTo(FLIGHT_DESTINATION);
        assertThat(flight.getTime()).isEqualTo(new SimpleDateFormat("HH:mm").parse(FLIGHT_TIME));
    }

    @Test(expected = FlightParseException.class)
    public void shouldThrowParseExceptionForMoreNumberOfTokens() throws Exception {
        parse(format("%s %s %s %s 10", FLIGHT_ID, FLIGHT_GATE, FLIGHT_DESTINATION, FLIGHT_TIME));
    }

    @Test(expected = FlightParseException.class)
    public void shouldThrowParseExceptionForLessNumberOfTokens() throws Exception {
        parse(format("%s %s %s", FLIGHT_ID, FLIGHT_GATE, FLIGHT_DESTINATION));
    }

    @Test(expected = FlightParseException.class)
    public void shouldThrowParseExceptionForNullInput() throws Exception {
        parse(null);
    }

}