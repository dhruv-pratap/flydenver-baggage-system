package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.ConveyorParseException;

import java.util.StringTokenizer;

/**
 * @author Dhruv Pratap
 */
public class Conveyor {

    private Node startNode;
    private Node endNode;
    private int travelTime;

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

        StringTokenizer stringTokenizer = new StringTokenizer(formattedString);
        if (stringTokenizer.countTokens() != 3) {
            throw new ConveyorParseException("Expecting at least 3 tokens");
        }
        return new Conveyor(
                new Node(stringTokenizer.nextToken()),
                new Node(stringTokenizer.nextToken()),
                Integer.valueOf(stringTokenizer.nextToken())
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conveyor conveyor = (Conveyor) o;

        if (!endNode.equals(conveyor.endNode)) return false;
        if (!startNode.equals(conveyor.startNode)) return false;

        return true;
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
