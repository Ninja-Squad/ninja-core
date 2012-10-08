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

package com.ninja_squad.core.i18n;

import java.util.MissingResourceException;

import javax.annotation.Nonnull;

/**
 * Strategy used to handle missing resources (i.e. the resource bundle is not found,
 * or the key is not found in the resource bundle)
 * @author JB Nizet
 */
public interface MissingResourceStrategy {

    /**
     * Handles the given exception, for the given key. Implementations are allowed to return
     * a default String derived from the key or <code>null</code>, or to throw a runtime
     * exception
     * @param key the key for which a String was asked
     * @param e the exception that was thrown, either when getting the resource bundle, or
     * when getting the STring in this bundle
     */
    String handleMissingResource(@Nonnull String key, @Nonnull MissingResourceException e);
}
