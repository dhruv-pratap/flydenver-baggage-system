package com.flydenver.baggage.aggregate

import com.flydenver.baggage.entity.Conveyor
import com.flydenver.baggage.entity.Node
import com.flydenver.baggage.exception.DuplicateConveyerException
import com.flydenver.baggage.exception.UnknownNodeException
import org.junit.Before
import org.junit.Test

import static com.flydenver.baggage.aggregate.ConveyorSystem.parse
import static org.assertj.core.api.Assertions.assertThat

class ConveyorSystemTest {

    def NODE_A = "A"
    def NODE_B = "B"
    def NODE_C = "C"
    def TRAVEL_TIME = 10

    def conveyorSystem

    @Before
    void setUp() throws Exception {
        conveyorSystem = new ConveyorSystem([new Conveyor(new Node(NODE_A), new Node(NODE_B), TRAVEL_TIME)] as Set)
    }

    @Test
    void shouldHaveImmutableConveyorSet() throws Exception {
        conveyorSystem.getConveyors().clear()
        assertThat(conveyorSystem.getConveyors()).isNotNull().isNotEmpty()
    }

    @Test
    void shouldHaveImmutableNodeSet() throws Exception {
        conveyorSystem.getNodes().clear()
        assertThat(conveyorSystem.getNodes()).isNotNull().isNotEmpty()
    }

    @Test(expected = DuplicateConveyerException.class)
    void shouldThrowExceptionWheDuplicateConveyorInformationIsAdded() throws Exception {
        conveyorSystem.addConveyor(new Conveyor(new Node(NODE_A), new Node(NODE_B), TRAVEL_TIME))
    }

    @Test
    void shouldAllowToAddConveyorWithExistingSourceButDifferentDestination() throws Exception {
        conveyorSystem.addConveyor(new Conveyor(new Node(NODE_A), new Node(NODE_C), TRAVEL_TIME))
        assertThat(conveyorSystem.getNodes()).hasSize(3)
        assertThat(conveyorSystem.getConveyors()).hasSize(4)
    }

    @Test
    void shouldAllowToAddConveyorWithDifferentSourceButExistingDestination() throws Exception {
        conveyorSystem.addConveyor(new Conveyor(new Node(NODE_C), new Node(NODE_B), TRAVEL_TIME))
        assertThat(conveyorSystem.getNodes()).hasSize(3)
        assertThat(conveyorSystem.getConveyors()).hasSize(4)
    }

    @Test
    void shouldParseMultiLineFormattedInput() throws Exception {
        def conveyorSystem = parse([
            "$NODE_A $NODE_B $TRAVEL_TIME" as String,
            "$NODE_A $NODE_C $TRAVEL_TIME" as String,
            "$NODE_B $NODE_C $TRAVEL_TIME" as String
        ])
        assertThat(conveyorSystem.getNodes()).hasSize(3)
        assertThat(conveyorSystem.getConveyors()).hasSize(6)
    }

    @Test(expected = UnknownNodeException.class)
    void shouldThrowExceptionWhenCannotFindNode() throws Exception {
        conveyorSystem.findNodeById("X")
    }

    @Test
    void shouldReturnAnExistingNodeWhenExists() throws Exception {
        assertThat(conveyorSystem.findNodeById("A")).isNotNull().isEqualTo(new Node(NODE_A))
    }
}