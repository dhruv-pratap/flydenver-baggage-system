package com.flydenver.baggage.entity;

import com.flydenver.baggage.exception.ConveyorParseException;

import static java.lang.Integer.valueOf;

/**
 * @author Dhruv Pratap
 */
public class Conveyor {

    private final Node startNode;
    private final Node endNode;
    private final int travelTime;

    public Conveyor(Node startNode, Node endNode, int travelTime) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.travelTime = travelTime;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public static Conveyor parse(String formattedString) {
        if (formattedString == null) {
            throw new ConveyorParseException();
        }

        String[] tokens = formattedString.split(" ");
        if (tokens.length != 3) {
            throw new ConveyorParseException("Expecting at least 3 tokens");
        }
        return new Conveyor(
                new Node(tokens[0]),
                new Node(tokens[1]),
                valueOf(tokens[2])
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conveyor conveyor = (Conveyor) o;
        return endNode.equals(conveyor.endNode) && startNode.equals(conveyor.startNode);
    }

    @Override
    public int hashCode() {
        int result = startNode.hashCode();
        result = 31 * result + endNode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return startNode + " " + endNode + " " + travelTime;
    }
}
