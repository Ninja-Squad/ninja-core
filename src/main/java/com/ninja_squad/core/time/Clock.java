package com.ninja_squad.core.time;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Tests are fragile when the tested unit depends on the current time, because the test
 * might pass at the time it was written, but fail some time later. To make sure that this doesn't
 * happen, methods should not use <code>System.currentTimeMillis()</code>, <code>new Date()</code>,
 * <code>Calendar.getInstance()</code> and any other methods that returns the current time. Instead, they
 * should ask the current time to an injected (or at least injectable) Clock instance. This allows the
 * unit test to inject a fake clock, which always returns the same current time, or which starts at a given
 * time rather than starting from the current time.
 * <br/>
 * Such fake instances can be created (as well as the real clock) by the {@link Clocks} factory class.
 * </p>
 * <p>
 * Note that if the superior joda-time API is used, the {@link JodaClock} interface and its {@link JodaClocks}
 * factory should be used instead.
 * </p>
 * @author JB
 */
public interface Clock {

    /**
     * Returns the current time, in milliseconds
     */
    long nowInMillis();

    /**
     * Returns the current time, as a Date instance
     */
    Date nowAsDate();

    /**
     * Returns the current time, as a Calendar instance
     */
    Calendar nowAsCalendar();
}
