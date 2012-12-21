/*
 * The MIT License
 *
 * Copyright (c) 2012, Ninja Squad
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ninja_squad.core.retry;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * A builder used to configure and create a {@link Retryer}.
 * @author JB Nizet
 *
 * @param <V> the type of the retryer return value
 */
public final class RetryerBuilder<V> {
    private StopStrategy stopStrategy;
    private WaitStrategy waitStrategy;
    private Predicate<Attempt<V>> rejectionPredicate = Predicates.alwaysFalse();

    private RetryerBuilder() {
    }

    /**
     * Constructs a new builder
     * @return the new builder
     */
    public static <V> RetryerBuilder<V> newBuilder() {
        return new RetryerBuilder<V>();
    }

    /**
     * Sets the wait strategy used to decide how long to sleep between failed attempts.
     * The default strategy is to retry immediately after a failed attempt.
     * @param waitStrategy the strategy used to sleep between failed attempts
     * @return <code>this</code>
     * @throws IllegalStateException if a wait strategy has already been set.
     */
    public RetryerBuilder<V> withWaitStrategy(@Nonnull WaitStrategy waitStrategy) throws IllegalStateException {
        Preconditions.checkNotNull(waitStrategy, "waitStrategy may not be null");
        Preconditions.checkState(this.waitStrategy == null,
                                 "a wait strategy has already been set %s",
                                 this.waitStrategy);
        this.waitStrategy = waitStrategy;
        return this;
    }

    /**
     * Sets the stop strategy used to decide when to stop retrying. The default strategy
     * is to retry until the call succeeds, indefinitely.
     * @param stopStrategy the strategy used to sleep between failed attempts
     * @return <code>this</code>
     * @throws IllegalStateException if a stop strategy has already been set.
     */
    public RetryerBuilder<V> withStopStrategy(StopStrategy stopStrategy) {
        Preconditions.checkNotNull(stopStrategy, "stopStrategy may not be null");
        Preconditions.checkState(this.stopStrategy == null,
                                 "a stop strategy has already been set %s",
                                 this.stopStrategy);
        this.stopStrategy = stopStrategy;
        return this;
    }

    /**
     * Configures the retryer to retry if an exception (i.e. any <code>Exception</code> or subclass
     * of <code>Exception</code>) is thrown by the call.
     * @return <code>this</code>
     */
    public RetryerBuilder<V> retryIfException() {
        rejectionPredicate = Predicates.or(rejectionPredicate, new ExceptionClassPredicate<V>(Exception.class));
        return this;
    }

    /**
     * Configures the retryer to retry if a runtime exception (i.e. any <code>RuntimeException</code> or subclass
     * of <code>RuntimeException</code>) is thrown by the call.
     * @return <code>this</code>
     */
    public RetryerBuilder<V> retryIfRuntimeException() {
        rejectionPredicate = Predicates.or(rejectionPredicate, new ExceptionClassPredicate<V>(RuntimeException.class));
        return this;
    }

    /**
     * Configures the retryer to retry if an exception of the given class (or subclass of the given class) is
     * thrown by the call.
     * @param exceptionClass the type of the exception which should cause the retryer to retry
     * @return <code>this</code>
     */
    public RetryerBuilder<V> retryIfExceptionOfType(@Nonnull Class<? extends Throwable> exceptionClass) {
        Preconditions.checkNotNull(exceptionClass, "exceptionClass may not be null");
        rejectionPredicate = Predicates.or(rejectionPredicate, new ExceptionClassPredicate<V>(exceptionClass));
        return this;
    }

    /**
     * Configures the retryer to retry if an exception satisfying the given predicate is
     * thrown by the call.
     * @param exceptionPredicate the predicate which causes a retry if satisfied
     * @return <code>this</code>
     */
    public RetryerBuilder<V> retryIfException(@Nonnull Predicate<Throwable> exceptionPredicate) {
        Preconditions.checkNotNull(exceptionPredicate, "exceptionPredicate may not be null");
        rejectionPredicate = Predicates.or(rejectionPredicate, new ExceptionPredicate<V>(exceptionPredicate));
        return this;
    }

    /**
     * Configures the retryer to retry if the result satisfies the given predicate.
     * @param resultPredicate a predicate applied to the result, and which causes the retryer
     * to retry if the predicate is satisfied
     * @return <code>this</code>
     */
    public RetryerBuilder<V> retryIfResult(@Nonnull Predicate<V> resultPredicate) {
        Preconditions.checkNotNull(resultPredicate, "resultPredicate may not be null");
        rejectionPredicate = Predicates.or(rejectionPredicate, new ResultPredicate<V>(resultPredicate));
        return this;
    }

    /**
     * Builds the retryer.
     * @return the built retryer.
     */
    public Retryer<V> build() {
        StopStrategy theStopStrategy = stopStrategy == null ? StopStrategies.neverStop() : stopStrategy;
        WaitStrategy theWaitStrategy = waitStrategy == null ? WaitStrategies.noWait() : waitStrategy;

        return new Retryer<V>(theStopStrategy, theWaitStrategy, rejectionPredicate);
    }

    /**
     * A predicate on an Attempt which checks that the attempt has an exception and that this exception
     * is of a given class (or subclass of this given class)
     * @author JB Nizet
     *
     * @param <V> the type of the Attempt
     */
    private static final class ExceptionClassPredicate<V> implements Predicate<Attempt<V>> {

        private final Class<? extends Throwable> exceptionClass;

        public ExceptionClassPredicate(@Nonnull Class<? extends Throwable> exceptionClass) {
            this.exceptionClass = Preconditions.checkNotNull(exceptionClass);
        }

        @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "NP",
            justification = "This predicate is never called with a null argument")
        @Override
        public boolean apply(Attempt<V> attempt) {
            return attempt.hasException() && exceptionClass.isAssignableFrom(attempt.getExceptionCause().getClass());
        }
    }

    /**
     * A predicate on an Attempt which checks that the attempt has a result, and that this
     * result satisfies another predicate
     * @author JB Nizet
     *
     * @param <V> the type of the attempt
     */
    private static final class ResultPredicate<V> implements Predicate<Attempt<V>> {

        private final Predicate<V> delegate;

        public ResultPredicate(@Nonnull Predicate<V> delegate) {
            this.delegate = Preconditions.checkNotNull(delegate);
        }

        @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "NP",
            justification = "This predicate is never called with a null argument")
        @Override
        public boolean apply(Attempt<V> attempt) {
            if (!attempt.hasResult()) {
                return false;
            }
            V result = attempt.getResult();
            return delegate.apply(result);
        }
    }

    /**
     * A predicate on an Attempt which checks that the attempt has an exception, and that this
     * exception satisfies another predicate
     * @author JB Nizet
     *
     * @param <V> the type of the attempt
     */
    private static final class ExceptionPredicate<V> implements Predicate<Attempt<V>> {

        private final Predicate<Throwable> delegate;

        public ExceptionPredicate(@Nonnull Predicate<Throwable> delegate) {
            this.delegate = Preconditions.checkNotNull(delegate);
        }

        @Override
        @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "NP",
            justification = "This predicate is never called with a null argument")
        public boolean apply(Attempt<V> attempt) {
            return attempt.hasException() && delegate.apply(attempt.getExceptionCause());
        }
    }
}
