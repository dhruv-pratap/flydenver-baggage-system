package com.flydenver.baggage.exception;

/**
 * @author Dhruv Pratap
 */
public class FlightParseException extends RuntimeException {

    public FlightParseException() {
        super();
    }

    public FlightParseException(String message) {
        super(message);
    }

    public FlightParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
