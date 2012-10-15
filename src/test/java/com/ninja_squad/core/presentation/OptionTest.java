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
        Option<Integer> o1 = Option.forValue(1);
        assertEquals(1, o1.getValue().intValue());
        assertEquals("1", o1.getLabel());
    }

    @Test
    public void forValueWithFunctionWorksAsExpected() {
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
