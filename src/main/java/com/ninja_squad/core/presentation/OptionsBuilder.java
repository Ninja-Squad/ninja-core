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

package com.ninja_squad.core.presentation;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.ninja_squad.core.i18n.EnumResources;

/**
 * <p>
 * A builder to construct a list of options. It allows transforming a collection of options, values, or enums into
 * a list of options, to prepend them with a custom null option, and to order them by value or label.
 * </p>
 * <p>Example usages:</p>
 * <pre>
 *     List&lt;Integer&gt; integers = Arrays.asList(1, 2, 3);
 *     List&lt;Option&lt;Integer&gt;&gt; options = OptionsBuilder.forValues(integers, Functions.toStringFunction())
 *                                                   .withNullOption(Option&lt;Integer&gt;nullSpaceOption())
 *                                                   .orderByValue(Ordering.&lt;Integer&gt;natural().reverse())
 *                                                   .toList();
 *     List&lt;Option&lt;Season&gt;&gt; seasons = OptionsBuilder.forEnumClass(Season.class, EnumResources.getInstance())
 *                                                  .orderByLabel()
 *                                                  .toList();
 * </pre>
 * @author JB Nizet
 *
 * @param <V> the type of options created by this builder
 */
public final class OptionsBuilder<V> {

    /**
     * The comparator which sorts options by their label
     */
    private static final Comparator<Option<?>> BY_LABEL_COMPARATOR = new Comparator<Option<?>>() {
        @Override
        public int compare(Option<?> o1, Option<?> o2) {
            return o1.getLabel().compareTo(o2.getLabel());
        }
    };

    /**
     * The list of non-null options
     */
    private final List<Option<V>> options;

    /**
     * The null option
     */
    @CheckForNull
    private Option<V> nullOption;

    /**
     * A flag indicating if the order has been set.
     */
    private boolean orderSet;

    private OptionsBuilder(List<Option<V>> options) {
        this.options = options;
    }

    /**
     * Creates a new builder instance allowing to construct a list of options which are created from an iteration of
     * objects.
     * @param options the options to sort and/or prepend with a null option
     * @return the created OptionsBuilder instance
     */
    public static <V, T> OptionsBuilder<V> forOptions(@Nonnull Iterable<Option<V>> options) {
        Preconditions.checkNotNull(options, "options may not be null");
        return new OptionsBuilder<V>(Lists.newArrayList(options));
    }

    /**
     * Creates a new builder instance allowing to construct a list of options which are created from an iteration of
     * option values.
     * @param values the values of the options
     * @param valueToLabel the function  which is used to get the label corresponding to each value
     * @return the created OptionsBuilder instance
     */
    public static <V> OptionsBuilder<V> forValues(@Nonnull Iterable<? extends V> values,
                                                  @Nonnull final Function<? super V, String> valueToLabel) {
        Preconditions.checkNotNull(values, "values may not be null");
        Preconditions.checkNotNull(valueToLabel, "valueToLabel may not be null");
        return forOptions(Iterables.transform(values, Option.<V>valueToOptionFunction(valueToLabel)));
    }

    /**
     * Creates a new builder instance allowing to construct a list of options containing one option per enum constant
     * of the given enum class.
     * @param enumClass the class of the enums to transform into options
     * @param enumResources the {@link EnumResources} instance used to get the label associated to each enum constant
     * @return the created OptionsBuilder instance
     */
    public static <V extends Enum<V>> OptionsBuilder<V> forEnumClass(@Nonnull Class<V> enumClass,
                                                                     @Nonnull EnumResources enumResources) {
        Preconditions.checkNotNull(enumClass, "enumClass may not be null");
        Preconditions.checkNotNull(enumResources, "enumResources may not be null");
        return forEnums(EnumSet.allOf(enumClass), enumResources);
    }

    /**
     * Creates a new builder instance allowing to construct a list of options containing one option per enum constant
     * of the given enum class.
     * @param enumConstants the enums to transform into options
     * @param enumResources the {@link EnumResources} instance used to get the label associated to each enum constant
     * @return the created OptionsBuilder instance
     */
    public static <V extends Enum<V>> OptionsBuilder<V> forEnums(@Nonnull Iterable<V> enumConstants,
                                                                 @Nonnull final EnumResources enumResources) {
        Preconditions.checkNotNull(enumConstants, "enumConstants may not be null");
        Preconditions.checkNotNull(enumResources, "enumResources may not be null");
        return forOptions(Iterables.transform(enumConstants, Option.<V>enumToOptionFunction(enumResources)));
    }

    /**
     * Allows adding a null option at the beginning of the list
     * @param nullOption the null option to add
     * @return this
     * @throws IllegalStateException if the given nullOption is not a null option
     */
    public OptionsBuilder<V> withNullOption(@Nonnull Option<V> nullOption) {
        Preconditions.checkNotNull(nullOption, "nullOption may not be null");
        Preconditions.checkArgument(nullOption.isNull(), "nullOption.isNull() must be true");
        Preconditions.checkState(this.nullOption == null, "nullOption has already been set");

        this.nullOption = nullOption;
        return this;
    }

    /**
     * Sorts the options by value, using the natural ordering of the values. The potential null option always stays at
     * the beginning of the list.
     * @return this
     * @throws ClassCastException if the values are not comparable
     */
    @SuppressWarnings("unchecked")
    public OptionsBuilder<V> orderByValue() {
        Preconditions.checkState(!orderSet, "order has already been set");
        Collections.sort(options, new ByValueComparator<V>((Comparator<? super V>) Ordering.natural()));
        return this;
    }

    /**
     * Sorts the options by value, using the given comparator to compare values. The potential null option always stays
     * at the beginning of the list, and its null value is not compared with the other values.
     * @return this
     */
    public OptionsBuilder<V> orderByValue(@Nonnull Comparator<? super V> valueComparator) {
        Preconditions.checkNotNull(valueComparator, "comparator may not be null");
        Preconditions.checkState(!orderSet, "order has already been set");
        Collections.sort(options, new ByValueComparator<V>(valueComparator));
        return this;
    }

    /**
     * Sorts the options by label, using the given comparator to compare values. The potential null option always stays
     * at the beginning of the list.
     * @return this
     */
    public OptionsBuilder<V> orderByLabel() {
        Preconditions.checkState(!orderSet, "order has already been set");
        Collections.sort(options, BY_LABEL_COMPARATOR);
        return this;
    }

    /**
     * Generates the list of options
     * @return the generated list
     */
    public List<Option<V>> toList() {
        List<Option<V>> result = Lists.newArrayListWithCapacity(options.size() + 1);
        if (nullOption != null) {
            result.add(nullOption);
        }
        result.addAll(options);
        return result;
    }

    /**
     * A comparator of options which delegates to another comparator which compares their values
     * @author JB Nizet
     *
     * @param <V> the type of the values to compare
     */
    private static final class ByValueComparator<V> implements Comparator<Option<V>> {

        private final Comparator<? super V> delegate;

        private ByValueComparator(Comparator<? super V> delegate) {
            this.delegate = delegate;
        }

        @Override
        public int compare(Option<V> o1, Option<V> o2) {
            return delegate.compare(o1.getValue(), o2.getValue());
        }

    }
}
