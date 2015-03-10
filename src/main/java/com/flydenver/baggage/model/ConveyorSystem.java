package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.DuplicateConveyerException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;

/**
 * @author Dhruv Pratap
 */
public class ConveyorSystem {

    private Set<Node> nodes;
    private Set<Conveyor> conveyors;

    private ConveyorSystem() {
        this.nodes = new HashSet<>();
        this.conveyors = new HashSet<>();
    }

    public ConveyorSystem(Set<Conveyor> conveyors, Set<Node> nodes) {
        this.nodes = new HashSet<>();
        this.nodes.addAll(nodes);
        this.conveyors = new HashSet<>();
        this.conveyors.addAll(conveyors);
    }

    public Set<Conveyor> getConveyors() {
        Set<Conveyor> copyOfConveyors = new HashSet<>();
        copyOfConveyors.addAll(conveyors);
        return copyOfConveyors;
    }

    public Set<Node> getNodes() {
        Set<Node> copyOfNodes = new HashSet<>();
        copyOfNodes.addAll(nodes);
        return copyOfNodes;
    }

    protected void addConveyor(Conveyor conveyor) {
        if(conveyors.contains(conveyor)) {
            throw new DuplicateConveyerException();
        }
        nodes.add(conveyor.getStartNode());
        nodes.add(conveyor.getEndNode());
        conveyors.add(conveyor);
        // Add reverse direction Conveyor as well
        conveyors.add(new Conveyor(conveyor.getEndNode(), conveyor.getStartNode(), conveyor.getTravelTime()));
    }

    public static ConveyorSystem parse(List<String> multiLineFormattedInput) {
        ConveyorSystem conveyorSystem = new ConveyorSystem();
        multiLineFormattedInput.forEach(line -> conveyorSystem.addConveyor(Conveyor.parse(line)));
        return conveyorSystem;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("# Section: Conveyor System\n");
        conveyors.forEach(c -> builder.append(c.toString()).append(lineSeparator()));
        return builder.toString();
    }

    public Stream<Conveyor> findConveyorsOriginatingFrom(final Node node) {
        return conveyors.parallelStream().filter(conveyor -> conveyor.getStartNode().equals(node));
    }

}
