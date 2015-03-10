package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.DuplicateFlightException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.lineSeparator;

/**
 * @author Dhruv Pratap
 */
public class FlightSchedules {

    //TODO: Should find a better way to deal with ARRIVAL
    private static final Flight ARRIVAL_FLIGHT = new Flight("ARRIVAL");
    private Set<Flight> flights;

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

    protected void addFlight(Flight flight) {
        if(flights.contains(flight)) {
            throw new DuplicateFlightException();
        }
        flights.add(flight);
    }

    public static FlightSchedules parse(List<String> multiLineFormattedInput) {
        FlightSchedules flightSchedules = new FlightSchedules();
        multiLineFormattedInput.forEach(line -> flightSchedules.addFlight(Flight.parse(line)));
        return flightSchedules;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("# Section: Departures\n");
        flights.forEach(flight -> builder.append(flight.toString()).append(lineSeparator()));
        return builder.toString();
    }
}
