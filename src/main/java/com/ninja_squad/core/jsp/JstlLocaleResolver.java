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

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.servlet.jsp.PageContext;

/**
 * Implementation of {@link LocaleResolver} which searches for the locale in the
 * <code>javax.servlet.jsp.jstl.core.Config.FMT_LOCALE</code> JSTL config attribute. If not found there, the locale
 * of the request is used. If not found there, the default locale is used.
 * @author JB Nizet
 */
public final class JstlLocaleResolver implements LocaleResolver {

    public static final JstlLocaleResolver INSTANCE = new JstlLocaleResolver();

    private JstlLocaleResolver() {
    }

    @Override
    @Nonnull
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
