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

import static org.junit.Assert.*;

import org.joda.time.DateMidnight;
import org.joda.time.Duration;
import org.joda.time.ReadableInstant;
import org.junit.Test;

public class JodaClocksTest {
    @Test
    public void realClockWorks() throws InterruptedException {
        testRunningClock(JodaClocks.realClock(), System.currentTimeMillis());
    }

    @Test
    public void stoppedAtWithInstantWorks() throws Exception {
        ReadableInstant instant = new DateMidnight(2012, 3, 18);
        assertEquals(instant.getMillis(), JodaClocks.stoppedAt(instant).nowInMillis());
        assertEquals(instant.getMillis(), JodaClocks.stoppedAt(instant).nowAsDateTime().getMillis());
    }

    @Test
    public void stoppedAtWithMillisWorks() throws Exception {
        ReadableInstant instant = new DateMidnight(2012, 3, 18);
        assertEquals(instant.getMillis(), JodaClocks.stoppedAt(instant.getMillis()).nowInMillis());
        assertEquals(instant.getMillis(), JodaClocks.stoppedAt(instant.getMillis()).nowAsDateTime().getMillis());
    }

    @Test
    public void startingAtWithMillisWorks() throws Exception {
        ReadableInstant instant = new DateMidnight(2012, 3, 18);
        testRunningClock(JodaClocks.startingAt(instant.getMillis()), instant.getMillis());
    }

    @Test
    public void startingAtWithInstantWorks() throws Exception {
        ReadableInstant instant = new DateMidnight(2012, 3, 18);
        testRunningClock(JodaClocks.startingAt(instant), instant.getMillis());
    }

    @Test
    public void startingInWithMillisWorks() throws Exception {
        ReadableInstant instant = new DateMidnight(2012, 3, 18);
        long offset = instant.getMillis() - System.currentTimeMillis();
        testRunningClock(JodaClocks.startingIn(offset), instant.getMillis());
    }

    @Test
    public void startingInWithDurationWorks() throws Exception {
        ReadableInstant instant = new DateMidnight(2012, 3, 18);
        long offset = instant.getMillis() - System.currentTimeMillis();
        testRunningClock(JodaClocks.startingIn(new Duration(offset)), instant.getMillis());
    }

    private void testRunningClock(JodaClock clock, long timeReference) throws InterruptedException {
        long t0 = System.currentTimeMillis();
        assertTrue(clock.nowInMillis() - timeReference < 50L);
        assertTrue(clock.nowAsDateTime().getMillis() - timeReference < 50L);
        Thread.sleep(100L);
        long t1 = System.currentTimeMillis();
        assertTrue(clock.nowInMillis() - (timeReference + (t1 - t0)) < 50L);
        assertTrue(clock.nowAsDateTime().getMillis() - (timeReference + (t1 - t0)) < 50L);
    }
}
