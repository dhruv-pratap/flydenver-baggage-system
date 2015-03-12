package com.flydenver.baggage.vo;

import com.flydenver.baggage.exception.IncorrectInputException;
import com.flydenver.baggage.aggregate.Baggages;
import com.flydenver.baggage.aggregate.ConveyorSystem;
import com.flydenver.baggage.aggregate.FlightSchedules;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Dhruv Pratap
 */
public class Input {

    private static final String SECTION_DELIMITER = "# Section:";

    private ConveyorSystem conveyorSystem;
    private FlightSchedules flightSchedules;
    private Baggages baggages;

    private Input() {
    }

    public ConveyorSystem getConveyorSystem() {
        return conveyorSystem;
    }

    public FlightSchedules getFlightSchedules() {
        return flightSchedules;
    }

    public Baggages getBaggages() {
        return baggages;
    }

    public static Input parse(String input) {
        if (input == null) {
            throw new IncorrectInputException();
        }

        String[] sections = input.split(SECTION_DELIMITER);
        if (sections.length != 4) {
            throw new IncorrectInputException("Unexpected number of sections in input. Expecting 3.");
        }

        Input parsed = new Input();
        parsed.conveyorSystem = ConveyorSystem.parse(getSectionAsListOfString(sections[1]));
        parsed.flightSchedules = FlightSchedules.parse(getSectionAsListOfString(sections[2]), parsed.conveyorSystem);
        parsed.baggages = Baggages.parse(getSectionAsListOfString(sections[3]), parsed.conveyorSystem, parsed.flightSchedules);
        return parsed;
    }

    private static List<String> getSectionAsListOfString(String section) {
        String[] listOfTokens = section.split("\n");
        return asList(listOfTokens).subList(1, listOfTokens.length);
    }
}
