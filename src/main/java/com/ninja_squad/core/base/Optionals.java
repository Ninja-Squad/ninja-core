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

package com.ninja_squad.core.base;

import com.google.common.base.Function;
import com.google.common.base.Optional;

/**
 * Utility class used to deal with Optionals.
 * @author JB Nizet
 */
public final class Optionals {

    /**
     * The unique instance of the <code>toOptional</code> function.
     */
    @SuppressWarnings("rawtypes")
    private static final Function TO_OPTIONAL = new Function() {
        @Override
        public Optional apply(Object input) {
            return Optional.fromNullable(input);
        }
    };

    private Optionals() {
    }

    /**
     * Returns a function that transforms a potentially <code>null</code> reference into an Optional,
     * using the {@link Optional#fromNullable(Object)} method. This function fills the gap between collections
     * containing potentially <code>null</code> values, and immutable collections that don't support null values.
     * For example, transforming an Iterable containing nulls into an ImmutableList is done by:
     * <code>
     *     ImmutableList.copyOf(Iterables.transform(stringCollection, Optionals.&lt;String&gt;toOptional()));
     * </code>
     * @return the function.
     */
    @SuppressWarnings("unchecked")
    public static <T> Function<T, Optional<T>> toOptional() {
        return TO_OPTIONAL;
    }
}
