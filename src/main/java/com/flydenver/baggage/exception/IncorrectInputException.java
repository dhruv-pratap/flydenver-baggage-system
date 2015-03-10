package com.flydenver.baggage.exception;

/**
 * @author Dhruv Pratap
 */
public class IncorrectInputException extends RuntimeException {

    public IncorrectInputException() {
        super();
    }

    public IncorrectInputException(String message) {
        super(message);
    }
}
