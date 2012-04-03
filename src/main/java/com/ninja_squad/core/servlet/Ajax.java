package com.ninja_squad.core.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class to deal with AJAX requests at server-side
 * @author JB
 */
public final class Ajax {
    private Ajax() {
    }

    /**
     * Tests if a request is an AJAX request, using the "X-Requested-With" HTTP header
     * @param request the request to test
     * @return <code>true</code> if the "X-Requested-With" HTTP header is present and has the
     * value "XMLHttpRequest"
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
