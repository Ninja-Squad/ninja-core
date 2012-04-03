package com.ninja_squad.core.time;

import org.joda.time.DateTime;

/**
 * <p>
 * This interface has the same goal as the {@link Clock} interface, but should be used when using
 * <a href="http://joda-time.sourceforge.net/">joda-time</a>, which is a date and time API that is superior
 * to the standard Java date and time API.
 * </p>
 * <p>
 * Given that <code>DateTime</code> provides methods to transform itself into a variety of other formats (
 * DateMidnight, localDateTime, etc.), this interface only provides access to the current time (real or fake)
 * in the form of a <code>DateTime</code> instance.
 * </p>
 * <p>
 * Fake and real instances of this interface can be created by the {@link JodaClocks} factory class.
 * </p>
 *
 * @author JB
 */
public interface JodaClock {

    /**
     * Returns the current time, in milliseconds
     */
    long nowInMillis();

    /**
     * Returns the current time as a DateTime object
     */
    DateTime nowAsDateTime();
}
