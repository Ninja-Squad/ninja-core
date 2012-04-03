package com.ninja_squad.core.retry;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * Factory class for instanced of {@link WaitStrategy}
 * @author JB
 */
public final class WaitStrategies {

    private static final WaitStrategy NO_WAIT_STRATEGY = new FixedWaitStrategy(0L);

    private WaitStrategies() {
    }

    /**
     * Returns a strategy consisting in not sleeping before retrying
     */
    public static WaitStrategy noWait() {
        return NO_WAIT_STRATEGY;
    }

    /**
     * Returns a strategy consisting in sleeping a fixed amount of time
     * before retrying
     * @param sleepTime the time to sleep
     * @param timeUnit the unit of the time to sleep
     * @throws IllegalStateException if the sleep time is &lt; 0.
     */
    public static WaitStrategy fixedWait(long sleepTime, @Nonnull TimeUnit timeUnit) throws IllegalStateException {
        Preconditions.checkNotNull(timeUnit, "The time unit may not be null");
        return new FixedWaitStrategy(timeUnit.toMillis(sleepTime));
    }

    /**
     * Returns a strategy consisting in sleeping a random amount of time
     * before retrying
     * @param maximumTime the maximum time to sleep
     * @param timeUnit the unit of the maximum time
     * @throws IllegalStateException if the maximum sleep time is &lt;= 0.
     */
    public static WaitStrategy randomWait(long maximumTime, @Nonnull TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit, "The time unit may not be null");
        return new RandomWaitStrategy(0L, timeUnit.toMillis(maximumTime));
    }

    /**
     * Returns a strategy consisting in sleeping a random amount of time
     * before retrying
     * @param minimumTime the minimum time to sleep
     * @param minimumTimeUnit the unit of the minimum time
     * @param maximumTime the maximum time to sleep
     * @param maximumTimeUnit the unit of the maximum time
     * @throws IllegalStateException if the minimum sleep time is &lt; 0, or if the
     * maximum sleep time is less than (or equals to) the minimum.
     */
    public static WaitStrategy randomWait(long minimumTime,
                                          @Nonnull TimeUnit minimumTimeUnit,
                                          long maximumTime,
                                          @Nonnull TimeUnit maximumTimeUnit) {
        Preconditions.checkNotNull(minimumTimeUnit, "The minimum time unit may not be null");
        Preconditions.checkNotNull(maximumTimeUnit, "The maximum time unit may not be null");
        return new RandomWaitStrategy(minimumTimeUnit.toMillis(minimumTime),
                                      maximumTimeUnit.toMillis(maximumTime));
    }

    /**
     * Returns a strategy consisting in sleeping a fixed amount of time after the first failed attempt,
     * and in incrementing the amount of time after each failed attempt
     * @param initialSleepTime the time to sleep before retrying the first time
     * @param initialSleepTimeUnit the unit of the initial sleep time
     * @param increment the increment added to the previous sleep time after each failed attempt
     * @param incrementTimeUnit the unit of the increment
     */
    public static WaitStrategy incrementingWait(long initialSleepTime,
                                                @Nonnull TimeUnit initialSleepTimeUnit,
                                                long increment,
                                                @Nonnull TimeUnit incrementTimeUnit) {
        Preconditions.checkNotNull(initialSleepTimeUnit, "The initial sleep time unit may not be null");
        Preconditions.checkNotNull(incrementTimeUnit, "The increment time unit may not be null");
        return new IncrementingWaitStrategy(initialSleepTimeUnit.toMillis(initialSleepTime),
                                            incrementTimeUnit.toMillis(increment));
    }

    /**
     * The implementation of the strategy which waits for a fixed given time after each failed attempt
     * @author JB
     */
    @Immutable
    private static final class FixedWaitStrategy implements WaitStrategy {
        private final long sleepTime;

        public FixedWaitStrategy(long sleepTime) {
            Preconditions.checkArgument(sleepTime >= 0L, "sleepTime must be >= 0 but is %d", sleepTime);
            this.sleepTime = sleepTime;
        }

        @Override
        public long computeSleepTime(int previousAttemptNumber, long delaySinceFirstAttemptInMillis) {
            return sleepTime;
        }
    }

    /**
     * The implementation of the strategy which waits a random (but ranged) time after each failed attempt
     * @author JB
     */
    @Immutable
    private static final class RandomWaitStrategy implements WaitStrategy {
        private static final Random RANDOM = new Random();
        private final long minimum;
        private final long maximum;

        public RandomWaitStrategy(long minimum, long maximum) {
            Preconditions.checkArgument(minimum >= 0, "minimum must be >= 0 but is %d", minimum);
            Preconditions.checkArgument(maximum > minimum,
                                        "maximum must be > minimum but maximum is %d and minimum is",
                                        maximum,
                                        minimum);

            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public long computeSleepTime(int previousAttemptNumber, long delaySinceFirstAttemptInMillis) {
            long t = Math.abs(RANDOM.nextLong() % (maximum - minimum));
            return t + minimum;
        }
    }

    /**
     * The implementation of the strategy which waits an initial time after the first failed attempt, and then
     * increments this delay after each subsequent failed attempt
     * @author JB
     */
    @Immutable
    private static final class IncrementingWaitStrategy implements WaitStrategy {
        private final long initialSleepTime;
        private final long increment;

        public IncrementingWaitStrategy(long initialSleepTime,
                                        long increment) {
            Preconditions.checkArgument(initialSleepTime >= 0L,
                                        "initialSleepTime must be >= 0 but is %d",
                                        initialSleepTime);
            this.initialSleepTime = initialSleepTime;
            this.increment = increment;
        }

        @Override
        public long computeSleepTime(int previousAttemptNumber, long delaySinceFirstAttemptInMillis) {
            long result = initialSleepTime + (increment * (previousAttemptNumber - 1));
            return result >= 0L ? result : 0L;
        }
    }
}
