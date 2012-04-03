package com.ninja_squad.core.servlet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class AjaxTest {
    @Test
    public void isAjaxWorks() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        assertFalse(Ajax.isAjax(mockRequest));

        when(mockRequest.getHeader("X-Requested-With")).thenReturn("hello");
        assertFalse(Ajax.isAjax(mockRequest));
        when(mockRequest.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        assertTrue(Ajax.isAjax(mockRequest));
    }
}
