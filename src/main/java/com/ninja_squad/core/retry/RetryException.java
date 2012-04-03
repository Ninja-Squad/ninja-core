package com.ninja_squad.core.retry;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * An exception indicating that none of the attempts of the retryer succeeded.
 * @author JB
 */
@Immutable
public final class RetryException extends Exception {

    private final int numberOfFailedAttempts;
    private final Attempt<?> lastFailedAttempt;

    /**
     * Constructor
     * @param lastFailedAttempt the last failed attempt
     */
    public RetryException(int numberOfFailedAttempts, @Nonnull Attempt<?> lastFailedAttempt) {
        Preconditions.checkNotNull(lastFailedAttempt);
        this.numberOfFailedAttempts = numberOfFailedAttempts;
        this.lastFailedAttempt = lastFailedAttempt;
    }

    /**
     * Constructor
     * @param lastFailedAttempt the last failed attempt
     * @param cause the exception (normally an InterruptedException) that has caused this exception to be thrown
     */
    public RetryException(int numberOfFailedAttempts, @Nonnull Attempt<?> lastFailedAttempt, Throwable cause) {
        super(cause);
        Preconditions.checkNotNull(lastFailedAttempt);
        this.numberOfFailedAttempts = numberOfFailedAttempts;
        this.lastFailedAttempt = lastFailedAttempt;
    }

    /**
     * Returns the number of failed attempts
     */
    public int getNumberOfFailedAttempts() {
        return numberOfFailedAttempts;
    }

    /**
     * Returns the last failed attempt
     */
    public Attempt<?> getLastFailedAttempt() {
        return lastFailedAttempt;
    }
}
