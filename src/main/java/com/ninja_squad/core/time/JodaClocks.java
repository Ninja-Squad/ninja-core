package com.ninja_squad.core.time;

import javax.annotation.Nonnull;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.ReadableInstant;

import com.google.common.base.Preconditions;

/**
 * Factory for JodaClock instances. <br/>
 * In production code, the injected (or default) JodaClock instance should be the one returned by
 * {@link #realClock()}.<br/>
 * In tests, the other factory methods allow returning clocks that return always the same time, or that return a time
 * starting at a different time from the actual current time.
 * @author JB
 */
public final class JodaClocks {

    /**
     * The unique real clock instance
     */
    private static final JodaClock REAL_CLOCK = new AbstractJodaClock() {

        @Override
        public long nowInMillis() {
            return System.currentTimeMillis();
        }
    };

    private JodaClocks() {
    }

    /**
     * Returns an instance of a real clock, that returns the actual current time.
     */
    public static JodaClock realClock() {
        return REAL_CLOCK;
    }

    /**
     * Returns an instance of a clock, that returns the current time with the given offset added.
     * @param offsetInMillis the offset, in milliseconds, that is added to the actual current time
     * @return a clock that returns the current time + the offset
     */
    public static JodaClock startingIn(long offsetInMillis) {
        return new FakeOffsetJodaClock(offsetInMillis);
    }

    /**
     * Returns an instance of a clock, that returns the current time with the given offset added.
     * @param offset the offset that is added to the actual current time
     * @return a clock that returns the current time + the offset
     */
    public static JodaClock startingIn(@Nonnull Duration offset) {
        Preconditions.checkNotNull(offset, "offset may not be null");
        return new FakeOffsetJodaClock(offset.getMillis());
    }

    /**
     * Returns an instance of a clock, that returns a current time with an offset added.
     * The offset is the difference between the given time and the actual current time
     * @param timeInMillis the time at which this clock starts running, from now
     * @return a clock that returns the current time + the offset
     */
    public static JodaClock startingAt(long timeInMillis) {
        return new FakeOffsetJodaClock(timeInMillis - System.currentTimeMillis());
    }

    /**
     * Returns an instance of a clock, that returns a current time with an offset added.
     * The offset is the difference between the given instant and the actual current instant
     * @param instant the instant at which this clock starts running, from now
     * @return a clock that returns the current time + the offset
     */
    public static JodaClock startingAt(@Nonnull ReadableInstant instant) {
        return new FakeOffsetJodaClock(instant.getMillis() - System.currentTimeMillis());
    }

    /**
     * Returns an instance of a clock, that always returns the given time when asked for
     * the current time (as is it was stopped, or broken)
     * @param instant the instant that the clock must return
     * @return the stopped clock
     */
    public static JodaClock stoppedAt(@Nonnull ReadableInstant instant) {
        return new FakeStoppedJodaClock(instant.getMillis());
    }

    /**
     * Returns an instance of a clock, that always returns the given time when asked for
     * the current time (as is it was stopped, or broken)
     * @param timeInMillis the time that the clock must return
     * @return the stopped clock
     */
    public static JodaClock stoppedAt(long timeInMillis) {
        return new FakeStoppedJodaClock(timeInMillis);
    }

    /**
     * Base class for joda clocks, which computes the current time as DateTime from
     * the current time in millis.
     * @author JB
     */
    private abstract static class AbstractJodaClock implements JodaClock {
        @Override
        public final DateTime nowAsDateTime() {
            return new DateTime(nowInMillis());
        }
    }

    /**
     * The implementation of JodaClock which returns the current time with an offset added.
     * @author JB
     */
    private static final class FakeOffsetJodaClock extends AbstractJodaClock {

        private final long offsetInMillis;

        private FakeOffsetJodaClock(long offsetInMillis) {
            this.offsetInMillis = offsetInMillis;
        }

        @Override
        public long nowInMillis() {
            return System.currentTimeMillis() + offsetInMillis;
        }
    };

    /**
     * The implementation of JodaClock which returns always the same current time.
     * @author JB
     */
    private static final class FakeStoppedJodaClock extends AbstractJodaClock {

        private final long timeInMillis;

        private FakeStoppedJodaClock(long timeInMillis) {
            this.timeInMillis = timeInMillis;
        }

        @Override
        public long nowInMillis() {
            return timeInMillis;
        }
    };
}
