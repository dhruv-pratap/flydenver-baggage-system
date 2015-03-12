package com.flydenver.baggage.aggregate;

import com.flydenver.baggage.entity.Bag;
import com.flydenver.baggage.entity.Conveyor;
import com.flydenver.baggage.entity.Flight;
import com.flydenver.baggage.entity.Node;
import com.flydenver.baggage.exception.DuplicateBagException;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.flydenver.baggage.aggregate.Baggages.parse;
import static java.lang.Long.valueOf;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class BaggagesTest {

    private static final String BAG_NUMBER = "0001";
    private static final String ENTRY_POINT = "A1";
    private static final String FLIGHT_ID = "UA17";
    private static final String INPUT_FORMAT = "%s %s %s";
    private static final String FLIGHT_GATE = "A11";

    private Baggages baggages;

    private ConveyorSystem conveyorSystem;
    private FlightSchedules flightSchedules;

    @Before
    public void setUp() throws Exception {
        Set<Bag> bags = new HashSet<>();
        bags.add(new Bag(valueOf(BAG_NUMBER), new Node(ENTRY_POINT), new Flight(FLIGHT_ID, null, null, null)));
        baggages = new Baggages(bags);

        Set<Conveyor> conveyors = new HashSet<>();
        conveyors.add(new Conveyor(new Node(ENTRY_POINT), new Node(FLIGHT_GATE), 10));
        conveyorSystem = new ConveyorSystem(conveyors);

        Set<Flight> flights = new HashSet<>();
        flights.add(new Flight(FLIGHT_ID, new Node(FLIGHT_GATE), "JFK", new Date()));
        flightSchedules = new FlightSchedules(flights);
    }

    @Test
    public void flightSetShouldBeImmutable() throws Exception {
        Set<Bag> bags = baggages.getBags();
        bags.clear();
        assertThat(baggages.getBags()).isNotNull().isNotEmpty();
    }

    @Test(expected = DuplicateBagException.class)
    public void addingDuplicateBagInformationShouldThrowException() throws Exception {
        Bag flight = new Bag(valueOf(BAG_NUMBER), new Node(ENTRY_POINT), new Flight(FLIGHT_ID, null, null, null));
        baggages.addBag(flight);
    }

    @Test
    public void shouldParseMultiLineFormattedInput() throws Exception {
        String[] multiLineFormattedInput = new String[]{
                format(INPUT_FORMAT, BAG_NUMBER, ENTRY_POINT, FLIGHT_ID)
        };
        Baggages baggages = parse(asList(multiLineFormattedInput), conveyorSystem, flightSchedules);
        assertThat(baggages.getBags()).hasSize(1);
    }
}