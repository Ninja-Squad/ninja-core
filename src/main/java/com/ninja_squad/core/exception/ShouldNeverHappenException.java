package com.ninja_squad.core.exception;

/**
 * This exception indicates that something happened that should never happen. It's useful when
 * a method calls a method that throws a checked exception, but we know that this exception
 * can (or at least should, unless there is a bug) in fact never be thrown. It's also a good idea
 * to throw such an exception in a default case of a switch or if/else statement, if this default
 * case should never happen.
 * @author JB
 *
 */
public class ShouldNeverHappenException extends IllegalStateException {

    private static final String DEFAULT_MESSAGE = "Something that should never happen has happened.";

    /**
     * Default constructor, using a default message
     */
    public ShouldNeverHappenException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructor, using the given message and chaining the given Throwable cause
     */
    public ShouldNeverHappenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor, using the given message
     */
    public ShouldNeverHappenException(String message) {
        super(message);
    }

    /**
     * Constructor, using the default message, and chaining the given Throwable cause
     */
    public ShouldNeverHappenException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
