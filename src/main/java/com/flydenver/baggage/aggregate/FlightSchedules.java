package com.flydenver.baggage.aggregate;

import com.flydenver.baggage.entity.Flight;
import com.flydenver.baggage.entity.Node;
import com.flydenver.baggage.exception.DuplicateFlightException;
import com.flydenver.baggage.exception.UnknownFlightException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.lineSeparator;

/**
 * @author Dhruv Pratap
 */
public class FlightSchedules {

    //TODO: Should find a better way to deal with ARRIVAL
    private static final Flight ARRIVAL_FLIGHT = new Flight("ARRIVAL", new Node("BaggageClaim"), null, null);
    private final Set<Flight> flights;

    private FlightSchedules() {
        this.flights = new HashSet<>();
        this.flights.add(ARRIVAL_FLIGHT);
    }

    public FlightSchedules(Set<Flight> flights) {
        this.flights = new HashSet<>();
        this.flights.add(ARRIVAL_FLIGHT);
        this.flights.addAll(flights);
    }

    public Set<Flight> getFlights() {
        Set<Flight> copyOfFlights = new HashSet<>();
        copyOfFlights.addAll(flights);
        return copyOfFlights;
    }

    void addFlight(Flight flight) {
        if(flights.contains(flight)) {
            throw new DuplicateFlightException();
        }
        flights.add(flight);
    }

    public Flight findFlightById(String flightId) {
        return flights.stream()
                .filter(flight -> flight.getId().equals(flightId)).findAny().orElseThrow(UnknownFlightException::new);
    }

    public static FlightSchedules parse(List<String> multiLineFormattedInput, ConveyorSystem conveyorSystem) {
        FlightSchedules flightSchedules = new FlightSchedules();
        multiLineFormattedInput.forEach(line -> flightSchedules.addFlight(Flight.parse(line, conveyorSystem)));
        return flightSchedules;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("# Section: Departures\n");
        flights.forEach(flight -> builder.append(flight.toString()).append(lineSeparator()));
        return builder.toString();
    }
}
