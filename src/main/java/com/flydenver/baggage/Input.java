package com.flydenver.baggage;

import com.flydenver.baggage.exception.IncorrectInputException;
import com.flydenver.baggage.model.Baggages;
import com.flydenver.baggage.model.ConveyorSystem;
import com.flydenver.baggage.model.FlightSchedules;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dhruv Pratap
 */
public class Input {

    public static final String SECTION_DELIMITER = "# Section:";

    private ConveyorSystem conveyorSystem;
    private FlightSchedules flightSchedules;
    private Baggages baggages;

    public Input(ConveyorSystem conveyorSystem, FlightSchedules flightSchedules, Baggages baggages) {
        this.conveyorSystem = conveyorSystem;
        this.flightSchedules = flightSchedules;
        this.baggages = baggages;
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

        return new Input(
                ConveyorSystem.parse(getSectionAsListOfString(sections[1])),
                FlightSchedules.parse(getSectionAsListOfString(sections[2])),
                Baggages.parse(getSectionAsListOfString(sections[3]))
        );
    }

    private static List<String> getSectionAsListOfString(String section) {
        String[] listOfTokens = section.split("\n");
        List<String> sectionAsListOfString = new ArrayList<String>();
        for (int i = 1; i < listOfTokens.length; i++) {
            sectionAsListOfString.add(listOfTokens[i]);
        }
        return sectionAsListOfString;
    }
}
