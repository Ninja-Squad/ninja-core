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

import static org.junit.Assert.*;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.collect.Sets;

public class WaitStrategiesTest {

    @Test
    public void testNoWait() {
        WaitStrategy noWait = WaitStrategies.noWait();
        assertEquals(0L, noWait.computeSleepTime(18, 9879L));
    }

    @Test
    public void testFixedWait() {
        WaitStrategy fixedWait = WaitStrategies.fixedWait(1000L, TimeUnit.MILLISECONDS);
        assertEquals(1000L, fixedWait.computeSleepTime(12, 6546L));
    }

    @Test
    public void testIncrementingWait() {
        WaitStrategy incrementingWait = WaitStrategies.incrementingWait(500L, TimeUnit.MILLISECONDS, 100L, TimeUnit.MILLISECONDS);
        assertEquals(500L, incrementingWait.computeSleepTime(1, 6546L));
        assertEquals(600L, incrementingWait.computeSleepTime(2, 6546L));
        assertEquals(700L, incrementingWait.computeSleepTime(3, 6546L));
    }

    @Test
    public void testRandomWait() {
        WaitStrategy randomWait = WaitStrategies.randomWait(1000L, TimeUnit.MILLISECONDS, 2000L, TimeUnit.MILLISECONDS);
        Set<Long> times = Sets.newHashSet();
        times.add(randomWait.computeSleepTime(1, 6546L));
        times.add(randomWait.computeSleepTime(1, 6546L));
        times.add(randomWait.computeSleepTime(1, 6546L));
        times.add(randomWait.computeSleepTime(1, 6546L));
        assertTrue(times.size() > 1); // if not, the random is not random
        for (long time : times) {
            assertTrue(time >= 1000L);
            assertTrue(time <= 2000L);
        }
    }

    @Test
    public void testRandomWaitWithoutMinimum() {
        WaitStrategy randomWait = WaitStrategies.randomWait(2000L, TimeUnit.MILLISECONDS);
        Set<Long> times = Sets.newHashSet();
        times.add(randomWait.computeSleepTime(1, 6546L));
        times.add(randomWait.computeSleepTime(1, 6546L));
        times.add(randomWait.computeSleepTime(1, 6546L));
        times.add(randomWait.computeSleepTime(1, 6546L));
        assertTrue(times.size() > 1); // if not, the random is not random
        for (long time : times) {
            assertTrue(time >= 0L);
            assertTrue(time <= 2000L);
        }
    }
}
