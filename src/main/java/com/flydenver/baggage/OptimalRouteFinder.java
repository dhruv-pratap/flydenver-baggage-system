package com.flydenver.baggage;

import com.flydenver.baggage.aggregate.ConveyorSystem;
import com.flydenver.baggage.aggregate.FlightSchedules;
import com.flydenver.baggage.entity.Bag;
import com.flydenver.baggage.vo.Route;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author Dhruv Pratap
 */
public class OptimalRouteFinder {

    private final Bag bag;
    private final ConveyorSystem conveyorSystem;
    private final FlightSchedules flightSchedules;

    public OptimalRouteFinder(Bag bag, ConveyorSystem conveyorSystem, FlightSchedules flightSchedules) {
        this.bag = bag;
        this.conveyorSystem = conveyorSystem;
        this.flightSchedules = flightSchedules;
    }

    public Route findOptimalRoute() {
        List<Route> originatingRoutes = conveyorSystem
                .findConveyorsOriginatingFrom(bag.getEntryPoint())
                .map(conveyor -> new Route(conveyor, bag.getFlight().getGate(), conveyorSystem, flightSchedules))
                .collect(toList());

        originatingRoutes.stream().forEach(Route::discover);

        return fetchAllTraversedRoutes(originatingRoutes)
                .stream()
                .filter(Route::isDestinationDiscovered)
                .min(comparing(Route::getTotalTravelTime))
                .get();
    }

    private List<Route> fetchAllTraversedRoutes(List<Route> parentRoutes) {
        List<Route> routes = new ArrayList<>();
        parentRoutes.forEach(route -> {
                    if (!route.getChildRoutes().isEmpty()) {
                        routes.addAll(fetchAllTraversedRoutes(route.getChildRoutes()));
                    }
                    routes.add(route);
                }
        );
        return routes;
    }
}
