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

package com.ninja_squad.core.time;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 * Factory for Clock instances. <br/>
 * In production code, the injected (or default) Clock instance should be the one returned by {@link #realClock()}.<br/>
 * In tests, the other factory methods allow returning clocks that return always the same time, or that return a time
 * starting at a different time from the actual current time.
 * @author JB Nizet
 */
public final class Clocks {

    /**
     * The unique real clock instance
     */
    private static final Clock REAL_CLOCK = new AbstractClock() {

        @Override
        public long nowInMillis() {
            return System.currentTimeMillis();
        }
    };

    private Clocks() {
    }

    /**
     * Returns an instance of a real clock, that returns the actual current time.
     */
    public static Clock realClock() {
        return REAL_CLOCK;
    }

    /**
     * Returns an instance of a clock, that returns the current time with the given offset added.
     * @param offsetInMillis the offset, in milliseconds, that is added to the actual current time
     * @return a clock that returns the current time + the offset
     */
    public static Clock startingIn(long offsetInMillis) {
        return new FakeOffsetClock(offsetInMillis);
    }

    /**
     * Returns an instance of a clock, that returns the current time with the given offset added.
     * @param offset the offset that is added to the actual current time
     * @param timeUnit the unit of the offset that is added to the current time
     * @return a clock that returns the current time + the offset
     */
    public static Clock startingIn(long offset, @Nonnull TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit, "timeUnit may not be null");
        return new FakeOffsetClock(timeUnit.toMillis(offset));
    }

    /**
     * Returns an instance of a clock, that returns a current time with an offset added.
     * The offset is the difference between the given time and the actual current time
     * @param timeInMillis the time at which this clock starts running, from now
     * @return a clock that returns the current time + the offset
     */
    public static Clock startingAt(long timeInMillis) {
        return new FakeOffsetClock(timeInMillis - System.currentTimeMillis());
    }

    /**
     * Returns an instance of a clock, that returns a current time with an offset added.
     * The offset is the difference between the given time and the actual current time
     * @param time the time at which this clock starts running, from now
     * @return a clock that returns the current time + the offset
     */
    public static Clock startingAt(@Nonnull Date time) {
        return new FakeOffsetClock(time.getTime() - System.currentTimeMillis());
    }

    /**
     * Returns an instance of a clock, that always returns the given time when asked for
     * the current time (as is it was stopped, or broken)
     * @param time the time that the clock must return
     * @return the stopped clock
     */
    public static Clock stoppedAt(@Nonnull Date time) {
        return new FakeStoppedClock(time.getTime());
    }

    /**
     * Returns an instance of a clock, that always returns the given time when asked for
     * the current time (as is it was stopped, or broken)
     * @param timeInMillis the time that the clock must return
     * @return the stopped clock
     */
    public static Clock stoppedAt(long timeInMillis) {
        return new FakeStoppedClock(timeInMillis);
    }

    /**
     * Base class for clocks, which computes the current time as Date and as Calendar from
     * the current time in millis.
     * @author JB Nizet
     */
    private abstract static class AbstractClock implements Clock {
        @Override
        public final Date nowAsDate() {
            return new Date(nowInMillis());
        }

        @Override
        public final Calendar nowAsCalendar() {
            Calendar result = Calendar.getInstance();
            result.setTimeInMillis(nowInMillis());
            return result;
        }
    }

    /**
     * The implementation of Clock which returns the current time with an offset added.
     * @author JB Nizet
     */
    private static final class FakeOffsetClock extends AbstractClock {

        private final long offsetInMillis;

        private FakeOffsetClock(long offsetInMillis) {
            this.offsetInMillis = offsetInMillis;
        }

        @Override
        public long nowInMillis() {
            return System.currentTimeMillis() + offsetInMillis;
        }
    }

    /**
     * The implementation of Clock which returns always the same current time.
     * @author JB Nizet
     */
    private static final class FakeStoppedClock extends AbstractClock {

        private final long timeInMillis;

        private FakeStoppedClock(long timeInMillis) {
            this.timeInMillis = timeInMillis;
        }

        @Override
        public long nowInMillis() {
            return timeInMillis;
        }
    }
}
