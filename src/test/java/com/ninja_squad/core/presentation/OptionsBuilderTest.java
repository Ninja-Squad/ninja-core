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

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.ninja_squad.core.i18n.EnumResources;
import com.ninja_squad.core.i18n.MissingResourceStrategies;
import com.ninja_squad.core.i18n.TestEnum;

public class OptionsBuilderTest {

    @SuppressWarnings("unchecked")
    @Test
    public void forOptionsWorksAsExpected() {
        Option<Integer> o1 = Option.newOption(1, "one");
        Option<Integer> o2 = Option.newOption(2, "two");
        assertEquals(Lists.newArrayList(o1, o2), OptionsBuilder.forOptions(Lists.newArrayList(o1, o2)).toList());
    }

    @Test
    public void forValuesWorksAsExpected() {
        List<Option<Integer>> result =
            OptionsBuilder.forValues(Lists.newArrayList(1, 2), Functions.toStringFunction()).toList();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getValue().intValue());
        assertEquals(2, result.get(1).getValue().intValue());
        assertEquals("1", result.get(0).getLabel());
        assertEquals("2", result.get(1).getLabel());
    }

    @Test
    public void forEnumClassWorksAsExpected() {
        EnumResources enumResources =
            EnumResources.getInstance().withMissingResourceStrategy(MissingResourceStrategies.JSTL);
        List<Option<TestEnum>> result =
            OptionsBuilder.forEnumClass(TestEnum.class, enumResources).toList();
        assertEquals(2, result.size());
        assertEquals(TestEnum.FOO, result.get(0).getValue());
        assertEquals(TestEnum.BAR, result.get(1).getValue());
        assertEquals("The default foo label", result.get(0).getLabel());
        assertEquals("???BAR???", result.get(1).getLabel());
    }

    @Test
    public void forEnumsWorksAsExpected() {
        EnumResources enumResources =
            EnumResources.getInstance().withMissingResourceStrategy(MissingResourceStrategies.JSTL);
        List<Option<TestEnum>> result =
            OptionsBuilder.forEnums(EnumSet.of(TestEnum.FOO), enumResources).toList();
        assertEquals(1, result.size());
        assertEquals(TestEnum.FOO, result.get(0).getValue());
        assertEquals("The default foo label", result.get(0).getLabel());
    }

    @Test
    public void orderByValueWorksAsExpected() {
        List<Option<Integer>> result =
            OptionsBuilder.forValues(Lists.newArrayList(2, 1), Functions.toStringFunction()).orderByValue().toList();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getValue().intValue());
        assertEquals(2, result.get(1).getValue().intValue());

        result =
            OptionsBuilder.forValues(Lists.newArrayList(2, 1), Functions.toStringFunction())
                          .orderByValue()
                          .withNullOption(Option.<Integer>nullEmptyOption())
                          .toList();
        assertEquals(3, result.size());
        assertNull(result.get(0).getValue());
        assertEquals(1, result.get(1).getValue().intValue());
        assertEquals(2, result.get(2).getValue().intValue());
    }

    @Test
    public void orderByValueWithComparatorWorksAsExpected() {
        List<Option<Integer>> result =
            OptionsBuilder.forValues(Lists.newArrayList(2, 1), Functions.toStringFunction()).orderByValue(Ordering.natural().reverse()).toList();
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getValue().intValue());
        assertEquals(1, result.get(1).getValue().intValue());

        result =
            OptionsBuilder.forValues(Lists.newArrayList(2, 1), Functions.toStringFunction())
                          .orderByValue(Ordering.natural().reverse())
                          .withNullOption(Option.<Integer>nullEmptyOption())
                          .toList();
        assertEquals(3, result.size());
        assertNull(result.get(0).getValue());
        assertEquals(2, result.get(1).getValue().intValue());
        assertEquals(1, result.get(2).getValue().intValue());
    }

    @Test
    public void orderByLabelWorksAsExpected() {
        List<Option<TestEnum>> result =
            OptionsBuilder.forValues(Lists.newArrayList(TestEnum.FOO, TestEnum.BAR), Functions.toStringFunction())
                          .orderByLabel()
                          .toList();
        assertEquals(2, result.size());
        assertEquals(TestEnum.BAR, result.get(0).getValue());
        assertEquals(TestEnum.FOO, result.get(1).getValue());

        result =
            OptionsBuilder.forValues(Lists.newArrayList(TestEnum.FOO, TestEnum.BAR), Functions.toStringFunction())
                          .orderByLabel()
                          .withNullOption(Option.<TestEnum>nullEmptyOption())
                          .toList();
        assertEquals(3, result.size());
        assertNull(result.get(0).getValue());
        assertEquals(TestEnum.BAR, result.get(1).getValue());
        assertEquals(TestEnum.FOO, result.get(2).getValue());
    }

    @Test(expected = ClassCastException.class)
    public void orderByValueFailsWithClassCastExceptionWhenNotComparable() {
        Object o1 = new Object();
        Object o2 = new Object();
        OptionsBuilder.forValues(Arrays.asList(o1, o2), Functions.toStringFunction()).orderByValue().toList();
    }
}
