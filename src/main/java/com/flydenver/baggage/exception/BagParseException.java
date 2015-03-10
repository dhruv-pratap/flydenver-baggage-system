package com.flydenver.baggage.exception;

/**
 * @author Dhruv Pratap
 */
public class BagParseException extends RuntimeException {

    public BagParseException() {
        super();
    }

    public BagParseException(String message) {
        super(message);
    }
}
