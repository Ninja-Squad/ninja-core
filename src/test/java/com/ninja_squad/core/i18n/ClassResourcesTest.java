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

package com.ninja_squad.core.i18n;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;


public class ClassResourcesTest {

    @Test
    public void getStringWorks() {
        assertEquals("The test label", ClassResources.forClass(ClassResourcesTest.class).getString("test"));
        assertEquals("Le libellé en français", ClassResources.forClass(ClassResourcesTest.class)
                                                             .withLocale(new Locale("zz"))
                                                             .getString("test"));
    }

    @Test(expected = IllegalStateException.class)
    public void getStringThrowsExceptionWhenNoBundle() {
        ClassResources.forClass(String.class).getString("test");
    }

    @Test(expected = IllegalStateException.class)
    public void getStringThrowsExceptionWhenNoKey() {
        ClassResources.forClass(ClassResourcesTest.class).getString("doesntExist");
    }

    @Test
    public void getStringReturnsNullWithReturnNullStrategy() {
        assertNull(ClassResources.forClass(ClassResourcesTest.class)
                                 .withMissingResourceStrategy(MissingResourceStrategies.RETURN_NULL)
                                 .getString("doesntExist"));
    }

    @Test
    public void getStringReturnsKeyWithReturnKeyStrategy() {
        assertEquals("doesntExist",
                     ClassResources.forClass(ClassResourcesTest.class)
                                   .withMissingResourceStrategy(MissingResourceStrategies.RETURN_KEY)
                                   .getString("doesntExist"));
    }

    @Test
    public void getStringReturnsKeyWithQuestionMarksWithJstlStrategy() {
        assertEquals("???doesntExist???",
                     ClassResources.forClass(ClassResourcesTest.class)
                                   .withMissingResourceStrategy(MissingResourceStrategies.JSTL)
                                   .getString("doesntExist"));
    }
}
