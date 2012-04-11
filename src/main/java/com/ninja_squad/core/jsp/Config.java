package com.ninja_squad.core.jsp;


import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 * Class used to modify (preferrably at application deployment time) the configuration of the ninja-core tag library.
 * @author JB
 */
public final class Config {
    private static volatile LocaleResolver localeResolver = JstlLocaleResolver.INSTANCE;

    private Config() {
    }

    /**
     * Returns the configured (or default) locale resolver. The value returned by default is an instance of
     * {@link JstlLocaleResolver}.
     */
    public static LocaleResolver getLocaleResolver() {
        return localeResolver;
    }

    /**
     * Sets a new {@link LocaleResolver}, used by all the tags using a locale
     * @param localeResolver the new locale resolver
     */
    public static void setLocaleResolver(@Nonnull LocaleResolver localeResolver) {
        Preconditions.checkNotNull(localeResolver, "localeResolver may not be null");
        Config.localeResolver = localeResolver;
    }
}
