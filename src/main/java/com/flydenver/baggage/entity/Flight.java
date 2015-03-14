package com.flydenver.baggage.entity;

import com.flydenver.baggage.exception.FlightParseException;
import com.flydenver.baggage.aggregate.ConveyorSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dhruv Pratap
 */
public class Flight {

    private String id;
    private Node gate;
    //TODO: Is destination a domain object?
    private String destination;
    private Date time;

    private Flight() {
    }

    public Flight(String id, Node gate, String destination, Date time) {
        this.id = id;
        this.gate = gate;
        this.destination = destination;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public Node getGate() {
        return gate;
    }

    public String getDestination() {
        return destination;
    }

    public Date getTime() {
        return time;
    }

    public static Flight parse(String formattedString, ConveyorSystem conveyorSystem) {
        if (formattedString == null) {
            throw new FlightParseException();
        }

        String[] tokens = formattedString.split(" ");
        if (tokens.length != 4) {
            throw new FlightParseException("Expecting at least 4 tokens");
        }
        try {
            return new Flight(
                    tokens[0],
                    conveyorSystem.findNodeById(tokens[1]),
                    tokens[2],
                    new SimpleDateFormat("HH:mm").parse(tokens[3]));
        } catch (ParseException e) {
            throw new FlightParseException("Unable to parse flight time. Expected format HH:mm", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && id.equals(((Flight) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id + " " + gate + " " + destination + " " + time;
    }
}
