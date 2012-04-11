package com.ninja_squad.core.jsp;

import java.util.Locale;

import javax.servlet.jsp.PageContext;

/**
 * Implementation of {@link LocaleResolver} which returns the locale of the request. If not found there, the
 * default locale is used.
 * @author JB
 */
public final class RequestLocaleResolver implements LocaleResolver {
    public static final RequestLocaleResolver INSTANCE = new RequestLocaleResolver();

    private RequestLocaleResolver() {
    }

    @Override
    public Locale resolveLocale(PageContext context) {
        Locale locale = context.getRequest().getLocale();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }
}