package com.flydenver.baggage;

import com.flydenver.baggage.model.ConveyorSystem;
import com.flydenver.baggage.model.FlightSchedules;
import com.flydenver.baggage.model.Node;
import com.flydenver.baggage.model.Route;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author Dhruv Pratap
 */
public class OptimalRouteFinder {

    private Node source;
    private Node destination;
    private ConveyorSystem conveyorSystem;
    private FlightSchedules flightSchedules;

    public OptimalRouteFinder(Node source, Node destination, ConveyorSystem conveyorSystem, FlightSchedules flightSchedules) {
        this.source = source;
        this.destination = destination;
        this.conveyorSystem = conveyorSystem;
        this.flightSchedules = flightSchedules;
    }

    public Route findOptimalRoute() {
        List<Route> originatingRoutes = conveyorSystem
                .findConveyorsOriginatingFrom(source)
                .map(conveyor -> new Route(conveyor, destination, conveyorSystem, flightSchedules))
                .collect(toList());

        originatingRoutes.parallelStream().forEach(Route::findRoute);

        return fetchAllTraversedRoutes(originatingRoutes)
                .parallelStream()
                .filter(Route::isSuccessPath)
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
