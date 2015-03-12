package com.flydenver.baggage.entity;

import com.flydenver.baggage.exception.ConveyorParseException;
import org.junit.Test;

import static com.flydenver.baggage.entity.Conveyor.parse;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class ConveyorTest {

    private static final String START_NODE = "A5";
    private static final String END_NODE = "A10";
    private static final int TRAVEL_TIME = 4;

    @Test
    public void shouldParseFormattedStringIntoConveyerObject() throws Exception {
        Conveyor conveyor = parse(format("%s %s %d", START_NODE, END_NODE, TRAVEL_TIME));
        assertThat(conveyor.getStartNode().getId()).isEqualTo(START_NODE);
        assertThat(conveyor.getEndNode().getId()).isEqualTo(END_NODE);
        assertThat(conveyor.getTravelTime()).isEqualTo(TRAVEL_TIME);
    }

    @Test(expected = ConveyorParseException.class)
    public void shouldThrowParseExceptionForMoreNumberOfTokens() throws Exception {
        parse(format("%s %s %d 10", START_NODE, END_NODE, TRAVEL_TIME));
    }

    @Test(expected = ConveyorParseException.class)
    public void shouldThrowParseExceptionForLessNumberOfTokens() throws Exception {
        parse(format("%s %s", START_NODE, END_NODE));
    }

    @Test(expected = ConveyorParseException.class)
    public void shouldThrowParseExceptionForNullInput() throws Exception {
        parse(null);
    }
}