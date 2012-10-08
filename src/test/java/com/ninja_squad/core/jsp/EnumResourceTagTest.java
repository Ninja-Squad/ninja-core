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

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Test;

import com.ninja_squad.core.i18n.TestEnum;

public class EnumResourceTagTest {
    @Test
    public void tagWritesWhenNoVar() throws JspException, IOException {
        EnumResourceTag tag = new EnumResourceTag();
        tag.setEnum(TestEnum.FOO);

        JspContext mockContext = mock(JspContext.class);
        JspWriter mockWriter = mock(JspWriter.class);
        when(mockContext.getOut()).thenReturn(mockWriter);
        tag.setJspContext(mockContext);
        EnumResourceTag spiedTag = spy(tag);
        doReturn(Locale.getDefault()).when(spiedTag).resolveLocale();
        spiedTag.doTag();
        verify(mockWriter).print("The default foo label");
    }

    @Test
    public void tagWritesWhenNoVarWithSuffix() throws JspException, IOException {
        EnumResourceTag tag = new EnumResourceTag();
        tag.setEnum(TestEnum.FOO);
        tag.setSuffix("description");
        JspContext mockContext = mock(JspContext.class);
        JspWriter mockWriter = mock(JspWriter.class);
        when(mockContext.getOut()).thenReturn(mockWriter);
        tag.setJspContext(mockContext);
        EnumResourceTag spiedTag = spy(tag);
        doReturn(Locale.getDefault()).when(spiedTag).resolveLocale();
        spiedTag.doTag();
        verify(mockWriter).print("The default foo description");
    }

    @Test
    public void tagCreatesAttributeWhenVar() throws JspException, IOException {
        doTestTagCreatesAttributeWhenVar(null, PageContext.PAGE_SCOPE);
        doTestTagCreatesAttributeWhenVar("page", PageContext.PAGE_SCOPE);
        doTestTagCreatesAttributeWhenVar("request", PageContext.REQUEST_SCOPE);
        doTestTagCreatesAttributeWhenVar("session", PageContext.SESSION_SCOPE);
        doTestTagCreatesAttributeWhenVar("application", PageContext.APPLICATION_SCOPE);
    }

    @Test(expected = JspException.class)
    public void tagThrowsJspExceptionWhenNotEnum() throws JspException, IOException {
        EnumResourceTag tag = new EnumResourceTag();
        tag.setEnum("hello");
        tag.doTag();
    }

    private void doTestTagCreatesAttributeWhenVar(String scopeString, int scope) throws JspException, IOException {
        EnumResourceTag tag = new EnumResourceTag();
        tag.setEnum(TestEnum.FOO);
        tag.setVar("theVar");
        tag.setScope(scopeString);
        JspContext mockContext = mock(JspContext.class);
        tag.setJspContext(mockContext);
        EnumResourceTag spiedTag = spy(tag);
        doReturn(Locale.getDefault()).when(spiedTag).resolveLocale();
        spiedTag.doTag();
        verify(mockContext).setAttribute("theVar", "The default foo label", scope);
    }
}
