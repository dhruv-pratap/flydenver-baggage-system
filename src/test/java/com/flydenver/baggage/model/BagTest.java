package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.BagParseException;
import org.junit.Test;

import static com.flydenver.baggage.model.Bag.parse;
import static java.lang.Long.valueOf;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class BagTest {

    private static String BAG_NUMBER = "0001";
    private static String ENTRY_POINT = "A10";
    private static String FLIGHT_ID = "UA17";

    @Test
    public void shouldParseFormattedStringIntoBagObject() throws Exception {
        Bag bag = parse(format("%s %s %s", BAG_NUMBER, ENTRY_POINT, FLIGHT_ID));
        assertThat(bag.getNumber()).isEqualTo(valueOf(BAG_NUMBER));
        assertThat(bag.getEntryPoint().getId()).isEqualTo(ENTRY_POINT);
        assertThat(bag.getFlight().getId()).isEqualTo(FLIGHT_ID);
    }

    @Test(expected = BagParseException.class)
    public void shouldThrowParseExceptionForMoreNumberOfTokens() throws Exception {
        parse(format("%s %s %s 10", BAG_NUMBER, ENTRY_POINT, FLIGHT_ID));
    }

    @Test(expected = BagParseException.class)
    public void shouldThrowParseExceptionForLessNumberOfTokens() throws Exception {
        parse(format("%s %s", BAG_NUMBER, ENTRY_POINT));
    }

    @Test(expected = BagParseException.class)
    public void shouldThrowParseExceptionForNullInput() throws Exception {
        parse(null);
    }
}