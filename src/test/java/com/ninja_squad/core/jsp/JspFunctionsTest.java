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
