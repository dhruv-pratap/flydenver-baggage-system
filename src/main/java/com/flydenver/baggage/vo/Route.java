package com.flydenver.baggage.vo;

import com.flydenver.baggage.aggregate.ConveyorSystem;
import com.flydenver.baggage.aggregate.FlightSchedules;
import com.flydenver.baggage.entity.Conveyor;
import com.flydenver.baggage.entity.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dhruv Pratap
 */
public class Route {

    private final Conveyor currentConveyor;
    private final Node destination;
    private final ConveyorSystem conveyorSystem;
    private final FlightSchedules flightSchedules;

    private final Set<Node> traversedNodes = new HashSet<>();
    private final List<Conveyor> traversedConveyors = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();
    private boolean successPath;
    private int totalTravelTime = 0;

    public Route(Conveyor currentConveyor, Node destination, ConveyorSystem conveyorSystem, FlightSchedules flightSchedules) {
        this.currentConveyor = currentConveyor;
        this.destination = destination;
        this.conveyorSystem = conveyorSystem;
        this.flightSchedules = flightSchedules;
    }

    public boolean isSuccessPath() {
        return successPath;
    }

    public List<Route> getChildRoutes() {
        return routes;
    }

    public int getTotalTravelTime() {
        return totalTravelTime;
    }

    public void findRoute() {
        traversedConveyors.add(currentConveyor);
        traversedNodes.add(currentConveyor.getStartNode());
        Node endNode = currentConveyor.getEndNode();
        traversedNodes.add(endNode);
        totalTravelTime += currentConveyor.getTravelTime();

        if (endNode.equals(destination)) {
            successPath = true;
        } else {
            conveyorSystem
                    .findConveyorsOriginatingFrom(endNode)
                    .filter(c -> !traversedNodes.contains(c.getEndNode()))
                    .forEach(this::forkNewRoute);
        }
    }

    private void forkNewRoute(Conveyor conveyor) {
        Route route = new Route(conveyor, destination, conveyorSystem, flightSchedules);
        route.totalTravelTime = totalTravelTime;
        route.traversedNodes.addAll(traversedNodes);
        route.traversedConveyors.addAll(traversedConveyors);
        route.findRoute();
        routes.add(route);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        traversedConveyors.forEach(conveyor -> builder.append(conveyor.getStartNode()).append(" "));
        builder.append(traversedConveyors.get(traversedConveyors.size() - 1).getEndNode()).append(" : ").append(totalTravelTime);
        return builder.toString();
    }
}
