package com.ninja_squad.core.jsp;

import java.util.Locale;

import javax.servlet.jsp.PageContext;

/**
 * Implementation of {@link LocaleResolver} which searches for the locale in the
 * <code>javax.servlet.jsp.jstl.core.Config.FMT_LOCALE</code> JSTL config attribute. If not found there, the locale
 * of the request is used. If not found there, the default locale is used.
 * @author JB
 */
public final class JstlLocaleResolver implements LocaleResolver {

    public static final JstlLocaleResolver INSTANCE = new JstlLocaleResolver();

    private JstlLocaleResolver() {
    }

    @Override
    public Locale resolveLocale(PageContext context) {
        Locale locale =
            (Locale) javax.servlet.jsp.jstl.core.Config.find(context,
                                                             javax.servlet.jsp.jstl.core.Config.FMT_LOCALE);
        if (locale == null) {
            locale = context.getRequest().getLocale();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }
}