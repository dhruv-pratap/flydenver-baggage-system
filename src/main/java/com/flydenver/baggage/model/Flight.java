package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.FlightParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author Dhruv Pratap
 */
public class Flight {

    private String id;
    private Node gate;
    //TODO: Is destination a domain object?
    private String destination;
    private Date time;

    public Flight(String id, Node gate, String destination, Date time) {
        this.id = id;
        this.gate = gate;
        this.destination = destination;
        this.time = time;
    }

    public Flight(String id) {
        this.id = id;
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

    public static Flight parse(String formattedString) {
        if (formattedString == null) {
            throw new FlightParseException();
        }

        StringTokenizer stringTokenizer = new StringTokenizer(formattedString);
        if (stringTokenizer.countTokens() != 4) {
            throw new FlightParseException("Expecting at least 4 tokens");
        }
        try {
            return new Flight(
                    stringTokenizer.nextToken(),
                    new Node(stringTokenizer.nextToken()),
                    stringTokenizer.nextToken(),
                    new SimpleDateFormat("HH:mm").parse(stringTokenizer.nextToken()));
        } catch (ParseException e) {
            throw new FlightParseException("Unable to parse flight time. Expected format HH:mm", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (!id.equals(flight.id)) return false;

        return true;
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
