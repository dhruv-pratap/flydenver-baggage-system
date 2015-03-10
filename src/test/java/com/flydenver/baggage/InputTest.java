package com.flydenver.baggage;

import com.flydenver.baggage.exception.IncorrectInputException;
import org.junit.Test;

import static com.flydenver.baggage.Input.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class InputTest {

    public static final String INPUT_STRING =
            "# Section: Conveyer System\n" +
            "A5 A1 6\n" +
            "A1 A2 10\n" +
            "# Section: Departures\n" +
            "UA10 A1 MIA 08:00\n" +
            "UA11 A5 JFK 09:00\n" +
            "# Section: Bags\n" +
            "0001 A5 UA10\n" +
            "0002 A1 UA11\n" +
            "0003 A1 ARRIVAL\n";

    @Test
    public void shouldParseValidInputString() throws Exception {
        Input input = parse(INPUT_STRING);
        assertThat(input.getBaggages()).isNotNull();
        assertThat(input.getConveyorSystem()).isNotNull();
        assertThat(input.getFlightSchedules()).isNotNull();
    }

    @Test(expected = IncorrectInputException.class)
    public void shouldThrowExceptionForMoreThanThreeSection() throws Exception {
        parse(INPUT_STRING + "# Section: Unknown");
    }

    @Test(expected = IncorrectInputException.class)
    public void shouldThrowExceptionForLessThanThreeSection() throws Exception {
        String inputString =
                "# Section: Conveyer System\n" +
                        "A5 A1 6\n" +
                        "A1 A2 10\n" +
                        "# Section: Departures\n" +
                        "UA10 A1 MIA 08:00\n" +
                        "UA11 A5 JFK 09:00\n";
        parse(inputString);
    }

    @Test(expected = IncorrectInputException.class)
    public void shouldThrowExceptionForNullInput() throws Exception {
        parse(null);
    }
}