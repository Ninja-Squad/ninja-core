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
 * @author JB Nizet
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
