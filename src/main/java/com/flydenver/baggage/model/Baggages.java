package com.flydenver.baggage.model;

import com.flydenver.baggage.exception.DuplicateBagException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.lineSeparator;

/**
 * @author Dhruv Pratap
 */
public class Baggages {

    private Set<Bag> bags;

    public Baggages() {
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

    protected void addBag(Bag bag) {
        if (bags.contains(bag)) {
            throw new DuplicateBagException();
        }
        bags.add(bag);
    }

    public static Baggages parse(List<String> multiLineFormattedInput) {
        Baggages baggages = new Baggages();
        multiLineFormattedInput.forEach(line -> baggages.addBag(Bag.parse(line)));
        return baggages;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("# Section: Bags\n");
        bags.forEach(bag -> builder.append(bag.toString()).append(lineSeparator()));
        return builder.toString();
    }
}
