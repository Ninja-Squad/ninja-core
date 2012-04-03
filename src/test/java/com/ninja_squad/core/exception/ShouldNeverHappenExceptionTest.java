package com.ninja_squad.core.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShouldNeverHappenExceptionTest {
    @Test
    public void constructorsWork() {
        String defaultMessage = "Something that should never happen has happened.";
        assertEquals(defaultMessage, new ShouldNeverHappenException().getMessage());
        Throwable t = new Throwable();
        assertEquals(defaultMessage, new ShouldNeverHappenException(t).getMessage());
        assertSame(t, new ShouldNeverHappenException(t).getCause());
        String message = "testMessage";
        assertEquals(message, new ShouldNeverHappenException(message).getMessage());
        assertEquals(message, new ShouldNeverHappenException(message, t).getMessage());
        assertSame(t, new ShouldNeverHappenException(message, t).getCause());
    }
}
