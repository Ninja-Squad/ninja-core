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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 * Immutable utility class used to get a String from a resource bundle associated with a class (using the
 * rules defined in {@link ResourceBundles#forClass(Class, Locale)}). By default, it uses the default
 * locale, and throws an IllegalStateException when the resource can't be found. The locale can be
 * changed, as well as the strategy used to handle MissingResourceException.
 *
 * @author JB Nizet
 */
public final class ClassResources {

    private final Class<?> clazz;
    private final Locale locale;
    private final MissingResourceStrategy missingResourceStrategy;

    private ClassResources(Class<?> clazz, Locale locale, MissingResourceStrategy missingResourceStrategy) {
        this.clazz = clazz;
        this.locale = locale;
        this.missingResourceStrategy = missingResourceStrategy;
    }

    /**
     * Creates an instance of this class for the given class, using the default locale and
     * the {@link MissingResourceStrategies#THROW_EXCEPTION} strategy
     * @param clazz the class for which resources are looked up
     * @return the created {@link ClassResources}
     */
    public static ClassResources forClass(@Nonnull Class<?> clazz) {
        Preconditions.checkNotNull(clazz, "the class may not be null");
        return new ClassResources(clazz, Locale.getDefault(), MissingResourceStrategies.THROW_EXCEPTION);
    }

    /**
     * Creates a copy of this instance, for another locale
     * @param locale the new locale
     * @return a copy of this instance
     */
    public ClassResources withLocale(@Nonnull Locale locale) {
        Preconditions.checkNotNull(locale, "the locale may not be null");
        return new ClassResources(clazz, locale, missingResourceStrategy);
    }

    /**
     * Creates a copy of this instance, with a new strategy for missing resources
     * @param strategy the new strategy
     * @return a copy of this instance
     */
    public ClassResources withMissingResourceStrategy(@Nonnull MissingResourceStrategy strategy) {
        Preconditions.checkNotNull(strategy, "the strategy may not be null");
        return new ClassResources(clazz, locale, strategy);
    }

    /**
     * Gets the string associated with the given key. If the bundle or the key
     * is not found, the {@link MissingResourceStrategy} associated with this instance
     * is invoked, and its result is returned instead.
     * @param key the key
     * @return the value associated with the key, or the result of the {@link MissingResourceStrategy}
     * @throws IllegalStateException if the resource is missing and the strategy is the default one
     */
    public String getString(@Nonnull String key) {
        Preconditions.checkNotNull(key, "the key may not be null");
        try {
            ResourceBundle bundle = ResourceBundles.internalForClass(clazz, locale);
            return bundle.getString(key);
        }
        catch (MissingResourceException e) {
            return missingResourceStrategy.handleMissingResource(key, e);
        }
    }
}
