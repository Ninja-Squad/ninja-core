package com.ninja_squad.core.presentation;

import java.io.Serializable;

import javax.annotation.Nonnull;

import com.google.common.base.Objects;

/**
 * <p>An option, that can be chosen in a select/combo box. An option has a value and a label. The value can be of
 * any type, and the label, which is typically internationalized, is what is displayed to the end user.
 * </p>
 * <p>Two options are eaqual if their values are equal. A special "null option" is often displayed at the beginning
 * of the list of options, to force the user to make a choice. Such a "null option" has a null value, and a
 * customizable label. Such null options are constructed using one of the <code>nullOption()</code> factory methods.
 * Two null options are always equal to each other, regardless of their label.</p>
 * <p>
 * If the type of the value of the option is immutable, then so is the Option wrapping it.
 * </p>
 * @author JB
 *
 * @param <V> The type of the value of the option.
 */
public final class Option<V> implements Serializable {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final Option NULL_SPACE_OPTION = new Option(null, " ");

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final Option NULL_EMPTY_OPTION = new Option(null, "");

    private final V value;
    private final String label;

    private Option(V value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * Creates a new option
     * @param value the value of the option
     * @param label the label of the option
     * @return the created option
     */
    public static <V> Option<V> newOption(@Nonnull V value, @Nonnull String label) {
        return new Option<V>(value, label);
    }

    /**
     * Creates a null option with the given label
     * @param label the label of the null option
     * @return the created null option.
     */
    public static <V> Option<V> nullOption(@Nonnull String label) {
        return new Option<V>(null, label);
    }

    /**
     * Creates a null option with a single space char as label. This is useful when used in a Swing combo box, because
     * an empty Strin results in an option that has a tiny height, and thus goes unnoticed and is not easily selectable.
     * @return a constant null option
     */
    @SuppressWarnings("unchecked")
    public static <V> Option<V> nullSpaceOption() {
        return NULL_SPACE_OPTION;
    }

    /**
     * Creates a null option with a single space char as label. This is useful when used in a Swing combo box, because
     * an empty Strin results in an option that has a tiny height, and thus goes unnoticed and is not easily selectable.
     * @return a constant null option
     */
    @SuppressWarnings("unchecked")
    public static <V> Option<V> nullEmptyOption() {
        return NULL_EMPTY_OPTION;
    }

    /**
     * Tests is this option is a null option
     * @return true if the value is <code>null</code>, false otherwise.
     */
    public boolean isNull() {
        return this.value == null;
    }

    /**
     * Returns the value of this option
     * @return the value of this option, which is <code>null</code> if the option is a null option
     */
    public V getValue() {
        return value;
    }

    /**
     * Returns the label of this option
     * @return the label of this option
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns <code>true</code> iff theobject is an option whose value is equal to this option's value, or if
     * both options are null options
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Option<?>)) {
            return false;
        }
        Option<?> option = (Option<?>) obj;
        return Objects.equal(this.value, option.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns the label of this option. Instances of this class can thus be used as elements of a Swing combo box,
     * without needing any specific renderer.
     */
    @Override
    public String toString() {
        return label;
    }
}
