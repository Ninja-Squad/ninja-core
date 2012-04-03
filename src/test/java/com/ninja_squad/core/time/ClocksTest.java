package com.ninja_squad.core.time;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ClocksTest {
    @Test
    public void realClockWorks() throws InterruptedException {
        testRunningClock(Clocks.realClock(), System.currentTimeMillis());
    }

    @Test
    public void stoppedAtWithDateWorks() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-03-18");
        assertEquals(date.getTime(), Clocks.stoppedAt(date).nowInMillis());
        assertEquals(date.getTime(), Clocks.stoppedAt(date).nowAsDate().getTime());
        assertEquals(date.getTime(), Clocks.stoppedAt(date).nowAsCalendar().getTimeInMillis());
    }

    @Test
    public void stoppedAtWithMillisWorks() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-03-18");
        assertEquals(date.getTime(), Clocks.stoppedAt(date.getTime()).nowInMillis());
        assertEquals(date.getTime(), Clocks.stoppedAt(date.getTime()).nowAsDate().getTime());
        assertEquals(date.getTime(), Clocks.stoppedAt(date.getTime()).nowAsCalendar().getTimeInMillis());
    }

    @Test
    public void startingAtWithMillisWorks() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-03-18");
        testRunningClock(Clocks.startingAt(date.getTime()), date.getTime());
    }

    @Test
    public void startingAtWithDateWorks() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-03-18");
        testRunningClock(Clocks.startingAt(date), date.getTime());
    }

    @Test
    public void startingInWithMillisWorks() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-03-18");
        long offset = date.getTime() - System.currentTimeMillis();
        testRunningClock(Clocks.startingIn(offset), date.getTime());
    }

    @Test
    public void startingInWithTimeUnitWorks() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-03-18");
        long offset = date.getTime() - System.currentTimeMillis();
        testRunningClock(Clocks.startingIn(offset * 1000L, TimeUnit.MICROSECONDS), date.getTime());
    }

    public void testRunningClock(Clock clock, long timeReference) throws InterruptedException {
        long t0 = System.currentTimeMillis();
        assertTrue(clock.nowInMillis() - timeReference < 50L);
        assertTrue(clock.nowAsDate().getTime() - timeReference < 50L);
        assertTrue(clock.nowAsCalendar().getTimeInMillis() - timeReference < 50L);
        Thread.sleep(100L);
        long t1 = System.currentTimeMillis();
        assertTrue(clock.nowInMillis() - (timeReference + (t1 - t0)) < 50L);
        assertTrue(clock.nowAsDate().getTime() - (timeReference + (t1 - t0)) < 50L);
        assertTrue(clock.nowAsCalendar().getTimeInMillis() - (timeReference + (t1 - t0)) < 50L);
    }
}
