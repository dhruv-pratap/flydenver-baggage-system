package com.flydenver.baggage.vo;

import com.flydenver.baggage.OptimalRouteFinder;
import com.flydenver.baggage.aggregate.Baggages;
import com.flydenver.baggage.aggregate.ConveyorSystem;
import com.flydenver.baggage.aggregate.FlightSchedules;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Dhruv Pratap
 */
public class Output {

    private final Input input;

    private final List<Route> optimalRoutes;

    public Output(Input input) {
        this.input = input;
        this.optimalRoutes = processInput();
    }

    List<Route> processInput() {
        ConveyorSystem conveyorSystem = input.getConveyorSystem();
        System.out.println(conveyorSystem);

        FlightSchedules flightSchedules = input.getFlightSchedules();
        System.out.println(flightSchedules);

        Baggages baggages = input.getBaggages();
        System.out.println(baggages);

        return baggages.getBags().stream()
                .map(bag -> new OptimalRouteFinder(bag, conveyorSystem, flightSchedules))
                .map(OptimalRouteFinder::findOptimalRoute)
                .collect(toList());
    }

    public void printOptimalRoutes() {
        optimalRoutes.forEach(route -> System.out.println("Optimal Route -> " + route));
    }

    public List<Route> getOptimalRoutes() {
        return optimalRoutes;
    }

}
