package com.flydenver.baggage.entity

import com.flydenver.baggage.exception.ConveyorParseException
import org.junit.Test

import static com.flydenver.baggage.entity.Conveyor.parse
import static org.assertj.core.api.Assertions.assertThat

class ConveyorTest {

    def START_NODE = "A5"
    def END_NODE = "A10"
    def TRAVEL_TIME = 4

    @Test
    void shouldParseFormattedStringIntoConveyorObject() throws Exception {
        def conveyor = parse("$START_NODE $END_NODE $TRAVEL_TIME")
        assertThat(conveyor.getStartNode().getId()).isEqualTo(START_NODE)
        assertThat(conveyor.getEndNode().getId()).isEqualTo(END_NODE)
        assertThat(conveyor.getTravelTime()).isEqualTo(TRAVEL_TIME)
    }

    @Test(expected = ConveyorParseException.class)
    void shouldThrowParseExceptionForMoreNumberOfTokens() throws Exception {
        parse("$START_NODE $END_NODE $TRAVEL_TIME 10")
    }

    @Test(expected = ConveyorParseException.class)
    void shouldThrowParseExceptionForLessNumberOfTokens() throws Exception {
        parse("$START_NODE $END_NODE")
    }

    @Test(expected = ConveyorParseException.class)
    void shouldThrowParseExceptionForNullInput() throws Exception {
        parse(null)
    }
}