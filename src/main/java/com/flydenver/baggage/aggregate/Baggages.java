package com.flydenver.baggage.aggregate;

import com.flydenver.baggage.entity.Bag;
import com.flydenver.baggage.exception.DuplicateBagException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.lineSeparator;

/**
 * @author Dhruv Pratap
 */
public class Baggages {

    private final Set<Bag> bags;

    private Baggages() {
        this.bags = new HashSet<>();
    }

    public Baggages(Set<Bag> bags) {
        this.bags = new HashSet<>();
        this.bags.addAll(bags);
    }

    public Set<Bag> getBags() {
        Set<Bag> copyOfBags = new HashSet<>();
        copyOfBags.addAll(bags);
        return copyOfBags;
    }

    void addBag(Bag bag) {
        if (bags.contains(bag)) {
            throw new DuplicateBagException();
        }
        bags.add(bag);
    }

    public static Baggages parse(List<String> multiLineFormattedInput, ConveyorSystem conveyorSystem, FlightSchedules flightSchedules) {
        Baggages baggages = new Baggages();
        multiLineFormattedInput.forEach(line -> baggages.addBag(Bag.parse(line, conveyorSystem, flightSchedules)));
        return baggages;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("# Section: Bags\n");
        bags.forEach(bag -> builder.append(bag.toString()).append(lineSeparator()));
        return builder.toString();
    }
}
