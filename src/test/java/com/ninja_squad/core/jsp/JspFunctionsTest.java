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

package com.ninja_squad.core.jsp;

import static org.junit.Assert.*;

import org.junit.Test;

public class JspFunctionsTest {
    @Test
    public void nlToBrWorks() {
        assertEquals("", JspFunctions.nlToBr(null));
        String input = "Hello\nWorld.\n&\"'<>";
        String escaped = JspFunctions.nlToBr(input);
        assertEquals("Hello<br/>World.<br/>&amp;&#034;&#039;&lt;&gt;", escaped);
    }

    @Test
    public void upperFirstWorks() {
        assertEquals("", JspFunctions.upperFirst(null));
        assertEquals("", JspFunctions.upperFirst(""));
        String input = "hello";
        String transformed = JspFunctions.upperFirst(input);
        assertEquals("Hello", transformed);
    }

    @Test
    public void escapeJsWorks() {
        assertEquals("", JspFunctions.escapeJs(null));
        String input = "\"'\t\r\n\b\f\\Hello";
        String escaped = JspFunctions.escapeJs(input);
        assertEquals("\\\"\\'\\t\\r\\n\\b\\f\\\\Hello", escaped);
    }
}
