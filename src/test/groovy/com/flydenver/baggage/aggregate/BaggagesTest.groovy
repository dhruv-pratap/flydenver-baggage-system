package com.flydenver.baggage.aggregate

import com.flydenver.baggage.entity.Bag
import com.flydenver.baggage.entity.Conveyor
import com.flydenver.baggage.entity.Flight
import com.flydenver.baggage.entity.Node
import com.flydenver.baggage.exception.DuplicateBagException
import org.junit.Before
import org.junit.Test

import static com.flydenver.baggage.aggregate.Baggages.parse
import static org.assertj.core.api.Assertions.assertThat

class BaggagesTest {

    def BAG_NUMBER = "0001"
    def ENTRY_POINT = "A1"
    def FLIGHT_ID = "UA17"
    def FLIGHT_GATE = "A11"

    def baggages
    def conveyorSystem
    def flightSchedules

    @Before
    void setUp() throws Exception {
        baggages = new Baggages([new Bag(BAG_NUMBER.toLong(), new Node(ENTRY_POINT), new Flight(id: FLIGHT_ID))] as Set)
        conveyorSystem = new ConveyorSystem([new Conveyor(new Node(ENTRY_POINT), new Node(FLIGHT_GATE), 10)] as Set)
        flightSchedules = new FlightSchedules([new Flight(FLIGHT_ID, new Node(FLIGHT_GATE), "JFK", new Date())] as Set)
    }

    @Test
    void shouldHaveImmutableFlightSet() throws Exception {
        baggages.getBags().clear()
        assertThat(baggages.getBags()).isNotNull().isNotEmpty()
    }

    @Test(expected = DuplicateBagException.class)
    void shouldThrowExceptionWhenDuplicateBagInformationIsAdded() throws Exception {
        baggages.addBag(new Bag(BAG_NUMBER.toLong(), new Node(ENTRY_POINT), new Flight(id: FLIGHT_ID)))
    }

    @Test
    void shouldParseMultiLineFormattedInput() throws Exception {
        def baggages = parse(["$BAG_NUMBER $ENTRY_POINT $FLIGHT_ID" as String], conveyorSystem, flightSchedules)
        assertThat(baggages.getBags()).hasSize(1)
    }
}