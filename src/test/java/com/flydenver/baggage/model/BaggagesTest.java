package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.DuplicateBagException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.flydenver.baggage.model.Baggages.parse;
import static java.lang.Long.valueOf;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class BaggagesTest {

    public static final String BAG_NUMBER = "0001";
    public static final String ENTRY_POINT = "A1";
    public static final String FLIGHT_ID = "UA17";
    public static final String INPUT_FORMAT = "%s %s %s";

    Baggages baggages;

    @Before
    public void setUp() throws Exception {
        Set<Bag> bags = new HashSet<Bag>();
        bags.add(new Bag(valueOf(BAG_NUMBER), new Node(ENTRY_POINT), new Flight(FLIGHT_ID)));
        baggages = new Baggages(bags);
    }

    @Test
    public void flightSetShouldBeImmutable() throws Exception {
        Set<Bag> bags = baggages.getBags();
        bags.clear();
        assertThat(baggages.getBags()).isNotNull().isNotEmpty();
    }

    @Test(expected = DuplicateBagException.class)
    public void addingDuplicateBagInformationShouldThrowException() throws Exception {
        Bag flight = new Bag(valueOf(BAG_NUMBER), new Node(ENTRY_POINT), new Flight(FLIGHT_ID));
        baggages.addBag(flight);
    }

    @Test
    public void shouldParseMultiLineFormattedInput() throws Exception {
        String[] multiLineFormattedInput = new String[]{
                format(INPUT_FORMAT, BAG_NUMBER, ENTRY_POINT, FLIGHT_ID)
        };
        Baggages baggages = parse(asList(multiLineFormattedInput));
        assertThat(baggages.getBags()).hasSize(1);
    }
}