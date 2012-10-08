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
 * @author JB Nizet
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
