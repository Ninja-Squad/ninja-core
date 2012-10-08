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


import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 * Class used to modify (preferrably at application deployment time) the configuration of the ninja-core tag library.
 * @author JB Nizet
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
