package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.DuplicateConveyerException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.flydenver.baggage.model.ConveyorSystem.parse;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ConveyorSystemTest {

    public static final String NODE_A = "A";
    public static final String NODE_B = "B";
    public static final String NODE_C = "C";
    public static final int TRAVEL_TIME = 10;
    public static final String INPUT_FORMAT = "%s %s %d";

    ConveyorSystem conveyorSystem;

    @Before
    public void setUp() throws Exception {
        Set<Conveyor> conveyors = new HashSet<Conveyor>();
        conveyors.add(new Conveyor(new Node(NODE_A), new Node(NODE_B), TRAVEL_TIME));

        Set<Node> nodes = new HashSet<Node>();
        nodes.add(new Node(NODE_A));
        nodes.add(new Node(NODE_B));
        conveyorSystem = new ConveyorSystem(conveyors, nodes);
    }

    @Test
    public void conveyorSetShouldBeImmutable() throws Exception {
        Set<Conveyor> conveyors = conveyorSystem.getConveyors();
        conveyors.clear();
        assertThat(conveyorSystem.getConveyors()).isNotNull().isNotEmpty();
    }

    @Test
    public void nodeSetShouldBeImmutable() throws Exception {
        Set<Node> nodes = conveyorSystem.getNodes();
        nodes.clear();
        assertThat(conveyorSystem.getNodes()).isNotNull().isNotEmpty();
    }

    @Test(expected = DuplicateConveyerException.class)
    public void addingDuplicateConveyorInformationShouldThrowException() throws Exception {
        Conveyor conveyor = new Conveyor(new Node(NODE_A), new Node(NODE_B), TRAVEL_TIME);
        conveyorSystem.addConveyor(conveyor);
    }

    @Test
    public void shouldAllowToAddConveyorWithExistingSourceButDifferentDestination() throws Exception {
        Conveyor conveyor = new Conveyor(new Node(NODE_A), new Node(NODE_C), TRAVEL_TIME);
        conveyorSystem.addConveyor(conveyor);
        assertThat(conveyorSystem.getNodes()).hasSize(3);
        assertThat(conveyorSystem.getConveyors()).hasSize(2);
    }

    @Test
    public void shouldAllowToAddConveyorWithDifferentSourceButExistingDestination() throws Exception {
        Conveyor conveyor = new Conveyor(new Node(NODE_C), new Node(NODE_B), TRAVEL_TIME);
        conveyorSystem.addConveyor(conveyor);
        assertThat(conveyorSystem.getNodes()).hasSize(3);
        assertThat(conveyorSystem.getConveyors()).hasSize(2);
    }

    @Test
    public void shouldParseMultiLineFormattedInput() throws Exception {
        String[] multiLineFormattedInput = new String[]{
                format(INPUT_FORMAT, NODE_A, NODE_B, TRAVEL_TIME),
                format(INPUT_FORMAT, NODE_A, NODE_C, TRAVEL_TIME),
                format(INPUT_FORMAT, NODE_B, NODE_C, TRAVEL_TIME)
        };
        ConveyorSystem conveyorSystem = parse(asList(multiLineFormattedInput));
        assertThat(conveyorSystem.getNodes()).hasSize(3);
        assertThat(conveyorSystem.getConveyors()).hasSize(3);
    }
}