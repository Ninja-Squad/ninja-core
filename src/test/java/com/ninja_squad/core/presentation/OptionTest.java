package com.ninja_squad.core.presentation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Functions;
import com.ninja_squad.core.i18n.EnumResources;
import com.ninja_squad.core.i18n.TestEnum;

public class OptionTest {
    @Test
    public void optionsWithSameValueAreEqual() {
        Option<Integer> o1 = Option.newOption(1, "one");
        Option<Integer> o2 = Option.newOption(1, "two");
        assertEquals(o1, o2);
        assertEquals(o1, o1);
        assertEquals(o1.hashCode(), o2.hashCode());
        assertFalse(o1.isNull());
    }

    @Test
    public void forValueWorksAsExpected() {
        Option<Integer> o1 = Option.forValue(1, Functions.toStringFunction());
        assertEquals(1, o1.getValue().intValue());
        assertEquals("1", o1.getLabel());
    }

    @Test
    public void forEnumWorksAsExpected() {
        Option<TestEnum> o1 = Option.forEnum(TestEnum.FOO, EnumResources.getInstance());
        assertEquals(TestEnum.FOO, o1.getValue());
        assertEquals("The default foo label", o1.getLabel());
    }

    @Test
    public void nullOptionsAreEqual() {
        Option<Integer> o1 = Option.nullOption("one");
        Option<Integer> o2 = Option.nullOption("two");
        assertEquals(o1, o2);
        assertEquals(o1, o1);
        assertEquals(o1.hashCode(), o2.hashCode());
        assertTrue(o1.isNull());
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
