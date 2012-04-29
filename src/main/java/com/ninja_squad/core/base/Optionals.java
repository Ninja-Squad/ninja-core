package com.ninja_squad.core.base;

import com.google.common.base.Function;
import com.google.common.base.Optional;

/**
 * Utility class used to deal with Optionals.
 * @author JB
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
     *     ImmutableList.copyOf(Iterables.transform(stringCollection, Optionals.<String>toOptional()));
     * </code>
     * @return the function.
     */
    @SuppressWarnings("unchecked")
    public static <T> Function<T, Optional<T>> toOptional() {
        return TO_OPTIONAL;
    }
}
