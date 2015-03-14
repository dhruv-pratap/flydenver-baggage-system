package com.flydenver.baggage.vo;

import com.flydenver.baggage.OptimalRouteFinder;
import com.flydenver.baggage.aggregate.Baggages;
import com.flydenver.baggage.aggregate.ConveyorSystem;
import com.flydenver.baggage.aggregate.FlightSchedules;
import com.flydenver.baggage.entity.Bag;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author Dhruv Pratap
 */
public class Output {

    private final Input input;

    private final Map<Bag,Route> optimalRoutes;

    public Output(Input input) {
        this.input = input;
        this.optimalRoutes = processInput();
    }

    Map<Bag,Route> processInput() {
        ConveyorSystem conveyorSystem = input.getConveyorSystem();
        System.out.println(conveyorSystem);

        FlightSchedules flightSchedules = input.getFlightSchedules();
        System.out.println(flightSchedules);

        Baggages baggages = input.getBaggages();
        System.out.println(baggages);

        return baggages.getBags().stream()
                .collect(toMap((bag) -> bag, (bag) -> new OptimalRouteFinder(bag, conveyorSystem, flightSchedules).findOptimalRoute()));
    }

    public void printOptimalRoutes() {
        optimalRoutes.forEach((bag, route) -> System.out.println("Optimal Route -> " + bag.getNumber() + " " + route));
    }

    public Map<Bag,Route> getOptimalRoutes() {
        return optimalRoutes;
    }
}
