package com.ninja_squad.core.presentation;

import static org.junit.Assert.*;

import org.junit.Test;

public class OptionTest {
    @Test
    public void optionsWithSameValueAreEqual() {
        Option<Integer> o1 = Option.newOption(1, "one");
        Option<Integer> o2 = Option.newOption(1, "two");
        assertEquals(o1, o2);
        assertEquals(o1, o1);
    }

    @Test
    public void nullOptionsAreEqual() {
        Option<Integer> o1 = Option.nullOption("one");
        Option<Integer> o2 = Option.nullOption("two");
        assertEquals(o1, o2);
        assertEquals(o1, o1);
    }

    @Test
    public void toStringReturnsLabel() {
        Option<Integer> o1 = Option.newOption(1, "one");
        assertEquals("one", o1.getLabel());
        assertEquals("one", o1.toString());
    }

    @Test
    public void nullSpaceOptionWorks() {
        assertEquals(" ", Option.nullSpaceOption().getLabel());
        assertTrue(Option.nullSpaceOption().isNull());
    }

    @Test
    public void nullEmptyOptionWorks() {
        assertEquals("", Option.nullEmptyOption().getLabel());
        assertTrue(Option.nullSpaceOption().isNull());
    }
}
