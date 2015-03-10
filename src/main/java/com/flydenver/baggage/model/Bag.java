package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.BagParseException;

import java.util.StringTokenizer;

/**
 * @author Dhruv Pratap
 */
public class Bag {

    private Long number;
    private Node entryPoint;
    private Flight flight;

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

    public static Bag parse(String formattedString) {
        if (formattedString == null) {
            throw new BagParseException();
        }

        StringTokenizer stringTokenizer = new StringTokenizer(formattedString);
        if (stringTokenizer.countTokens() != 3) {
            throw new BagParseException("Expecting at least 3 tokens");
        }
        return new Bag(
                Long.valueOf(stringTokenizer.nextToken()),
                new Node(stringTokenizer.nextToken()),
                new Flight(stringTokenizer.nextToken())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bag bag = (Bag) o;

        if (!number.equals(bag.number)) return false;

        return true;
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
