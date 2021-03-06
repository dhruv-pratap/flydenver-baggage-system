package com.flydenver.baggage.vo

import org.junit.Test

import static com.flydenver.baggage.vo.Input.parse
import static org.assertj.core.api.Assertions.assertThat

class OutputTest {

    def EXAMPLE_INPUT_STRING =
            "# Section: Conveyor System\n" +
                    "Concourse_A_Ticketing A5 5\n" +
                    "A5 BaggageClaim 5\n" +
                    "A5 A10 4\n" +
                    "A5 A1 6\n" +
                    "A1 A2 1\n" +
                    "A2 A3 1\n" +
                    "A3 A4 1\n" +
                    "A10 A9 1\n" +
                    "A9 A8 1\n" +
                    "A8 A7 1\n" +
                    "A7 A6 1\n" +
                    "# Section: Departures\n" +
                    "UA10 A1 MIA 08:00\n" +
                    "UA11 A1 LAX 09:00\n" +
                    "UA12 A1 JFK 09:45\n" +
                    "UA13 A2 JFK 08:30\n" +
                    "UA14 A2 JFK 09:45\n" +
                    "UA15 A2 JFK 10:00\n" +
                    "UA16 A3 JFK 09:00\n" +
                    "UA17 A4 MHT 09:15\n" +
                    "UA18 A5 LAX 10:15\n" +
                    "# Section: Bags\n" +
                    "0001 Concourse_A_Ticketing UA12\n" +
                    "0002 A5 UA17\n" +
                    "0003 A2 UA10\n" +
                    "0004 A8 UA18\n" +
                    "0005 A7 ARRIVAL\n"

    def EXPECTED_OUTPUT = [
            "Concourse_A_Ticketing A5 A1 : 11",
            "A5 A1 A2 A3 A4 : 9",
            "A2 A1 : 1",
            "A8 A9 A10 A5 : 6",
            "A7 A8 A9 A10 A5 BaggageClaim : 12"
    ]

    @Test
    void shouldProcessInputToGetOptimalRoutes() throws Exception {
        def input = parse(EXAMPLE_INPUT_STRING)
        assertThat(input.getConveyorSystem()).isNotNull()
        assertThat(input.getFlightSchedules()).isNotNull()
        assertThat(input.getBaggages()).isNotNull()

        def output = new Output(input)
        def optimalRoutes = output.getOptimalRoutes()
        output.printOptimalRoutes()

        def bags = input.getBaggages().getBags()
        bags.size().times {
            assertThat(optimalRoutes.keySet()[it]).isEqualTo(bags[it])
            assertThat(optimalRoutes.values()[it].toString()).isEqualTo(EXPECTED_OUTPUT[it])
        }
    }
}
