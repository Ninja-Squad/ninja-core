package com.ninja_squad.core.jsp;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.servlet.jsp.PageContext;

/**
 * A locale resolver is responsible for resolving the locale from a JSP context. Implementations must be
 * thread-safe.
 * @author JB
 */
public interface LocaleResolver {
    /**
     * Resolves the locale from the JSP context
     * @param context the page context
     * @return the locale to use (may not be <code>null</code>)
     */
    @Nonnull
    Locale resolveLocale(PageContext context);
}
