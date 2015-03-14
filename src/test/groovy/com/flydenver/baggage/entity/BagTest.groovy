package com.flydenver.baggage.entity

import com.flydenver.baggage.aggregate.ConveyorSystem
import com.flydenver.baggage.aggregate.FlightSchedules
import com.flydenver.baggage.exception.BagParseException
import org.junit.Before
import org.junit.Test

import static com.flydenver.baggage.entity.Bag.parse
import static org.assertj.core.api.Assertions.assertThat

class BagTest {

    def BAG_NUMBER = "0001"
    def ENTRY_POINT = "A10"
    def FLIGHT_ID = "UA17"
    def FLIGHT_GATE = "A11"

    def conveyorSystem
    def flightSchedules

    @Before
    void setUp() throws Exception {
        conveyorSystem = new ConveyorSystem([new Conveyor(new Node(ENTRY_POINT), new Node(FLIGHT_GATE), 10)] as Set)
        flightSchedules = new FlightSchedules([new Flight(FLIGHT_ID, new Node(FLIGHT_GATE), "JFK", new Date())] as Set)
    }

    @Test
    void shouldParseFormattedStringIntoBagObject() throws Exception {
        def bag = parse("$BAG_NUMBER $ENTRY_POINT $FLIGHT_ID", conveyorSystem, flightSchedules)
        assertThat(bag.getNumber()).isEqualTo(BAG_NUMBER.toLong())
        assertThat(bag.getEntryPoint().getId()).isEqualTo(ENTRY_POINT)
        assertThat(bag.getFlight().getId()).isEqualTo(FLIGHT_ID)
    }

    @Test(expected = BagParseException.class)
    void shouldThrowParseExceptionForMoreNumberOfTokens() throws Exception {
        parse("$BAG_NUMBER $ENTRY_POINT $FLIGHT_ID 10", conveyorSystem, flightSchedules)
    }

    @Test(expected = BagParseException.class)
    void shouldThrowParseExceptionForLessNumberOfTokens() throws Exception {
        parse("$BAG_NUMBER $ENTRY_POINT", conveyorSystem, flightSchedules)
    }

    @Test(expected = BagParseException.class)
    void shouldThrowParseExceptionForNullInput() throws Exception {
        parse(null, conveyorSystem, flightSchedules)
    }
}