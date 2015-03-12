package com.flydenver.baggage.entity;

import com.flydenver.baggage.exception.BagParseException;
import com.flydenver.baggage.aggregate.ConveyorSystem;
import com.flydenver.baggage.aggregate.FlightSchedules;

/**
 * @author Dhruv Pratap
 */
public class Bag {

    private final Long number;
    private final Node entryPoint;
    private final Flight flight;

    public Bag(Long number, Node entryPoint, Flight flight) {
        this.number = number;
        this.entryPoint = entryPoint;
        this.flight = flight;
    }


    public Long getNumber() {
        return number;
    }

    public Node getEntryPoint() {
        return entryPoint;
    }

    public Flight getFlight() {
        return flight;
    }

    public static Bag parse(String formattedString, ConveyorSystem conveyorSystem, FlightSchedules flightSchedules) {
        if (formattedString == null) {
            throw new BagParseException();
        }

        String[] tokens = formattedString.split(" ");
        if (tokens.length != 3) {
            throw new BagParseException("Expecting at least 3 tokens");
        }
        return new Bag(
                Long.valueOf(tokens[0]),
                conveyorSystem.findNodeById(tokens[1]),
                flightSchedules.findFlightById(tokens[2])
        );
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && number.equals(((Bag) o).number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder().append(number).append(" ").append(entryPoint).append(" ").append(flight.getId()).toString();
    }
}
