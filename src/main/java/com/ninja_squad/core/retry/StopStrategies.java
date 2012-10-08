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

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * Factory class for {@link StopStrategy} instances.
 * @author JB Nizet
 */
public final class StopStrategies {
    private static final StopStrategy NEVER_STOP = new NeverStopStrategy();

    private StopStrategies() {
    }

    /**
     * Returns a stop strategy which consists in never stopping retrying
     */
    public static StopStrategy neverStop() {
        return NEVER_STOP;
    }

    /**
     * Returns a stop strategy which consists in stopping after N failed attempts
     * @param attemptNumber the number of failed attempts before stopping
     */
    public static StopStrategy stopAfterAttempt(int attemptNumber) {
        return new StopAfterAttemptStrategy(attemptNumber);
    }

    /**
     * Returns a stop strategy which consists in stopping after a given delay
     * @param delayInMillis the delay, in milliseconds, starting with the start of the first attempt.
     */
    public static StopStrategy stopAfterDelay(long delayInMillis) {
        return new StopAfterDelayStrategy(delayInMillis);
    }

    /**
     * The implementation of the "never stop" strategy
     * @author JB Nizet
     */
    @Immutable
    private static final class NeverStopStrategy implements StopStrategy {
        @Override
        public boolean shouldStop(int previousAttemptNumber, long delaySinceFirstAttemptInMillis) {
            return false;
        }
    }

    /**
     * The implementation of the strategy which stops after a given number of attempts
     * @author JB Nizet
     */
    @Immutable
    private static final class StopAfterAttemptStrategy implements StopStrategy {
        private final int maxAttemptNumber;

        public StopAfterAttemptStrategy(int maxAttemptNumber) {
            Preconditions.checkArgument(maxAttemptNumber >= 1,
                                        "maxAttemptNumber must be >= 1 but is %d",
                                        maxAttemptNumber);
            this.maxAttemptNumber = maxAttemptNumber;
        }

        @Override
        public boolean shouldStop(int previousAttemptNumber, long delaySinceFirstAttemptInMillis) {
            return previousAttemptNumber >= maxAttemptNumber;
        }
    }

    /**
     * The implementation of the strategy that stops after a given delay
     * @author JB Nizet
     */
    @Immutable
    private static final class StopAfterDelayStrategy implements StopStrategy {
        private final long maxDelay;

        public StopAfterDelayStrategy(long maxDelay) {
            Preconditions.checkArgument(maxDelay >= 0L, "maxDelay must be >= 0 but is %d", maxDelay);
            this.maxDelay = maxDelay;
        }

        @Override
        public boolean shouldStop(int previousAttemptNumber, long delaySinceFirstAttemptInMillis) {
            return delaySinceFirstAttemptInMillis >= maxDelay;
        }
    }
}
