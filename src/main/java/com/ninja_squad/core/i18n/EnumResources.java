package com.ninja_squad.core.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 * Immutable utility class used to get a String from a resource bundle associated with enum constants
 * (using the rules defined in {@link ResourceBundles#forClass(Class, Locale)}). By default, it uses the default
 * locale, and throws an IllegalStateException when the resource can't be found. The locale can be
 * changed, as well as the strategy used to handle MissingResourceException.
 *
 * @author JB
 */
public final class EnumResources {

    private final Locale locale;
    private final MissingResourceStrategy missingResourceStrategy;

    private EnumResources(Locale locale, MissingResourceStrategy missingResourceStrategy) {
        this.locale = locale;
        this.missingResourceStrategy = missingResourceStrategy;
    }

    /**
     * Creates an instance of this class, with the default locale, and the
     * {@link MissingResourceStrategies#THROW_EXCEPTION} missing resource strategy
     * @return the created instance
     */
    public static EnumResources getInstance() {
        return new EnumResources(Locale.getDefault(), MissingResourceStrategies.THROW_EXCEPTION);
    }

    /**
     * Creates a copy of this instance for the given locale
     * @param locale the new locale
     * @return a copy of this instance
     */
    public EnumResources withLocale(@Nonnull Locale locale) {
        Preconditions.checkNotNull(locale, "the locale may not be null");
        return new EnumResources(locale, missingResourceStrategy);
    }

    /**
     * Creates a copy of this instance with a new strategy for missing resources
     * @param strategy the new strategy
     * @return a copy of this instance
     */
    public EnumResources withMissingResourceStrategy(@Nonnull MissingResourceStrategy strategy) {
        Preconditions.checkNotNull(locale, "the strategy may not be null");
        return new EnumResources(locale, strategy);
    }

    /**
     * Gets the string associated with the given enum constant. The key used to find the string
     * is the enum constant's name.
     * @param enumConstant the enum constant for which a String is asked.
     * @return the value associated with the key, or the result of the {@link MissingResourceStrategy}
     * @throws IllegalStateException if the resource is missing and the strategy is the default one
     */
    public <E extends Enum<E>> String getString(@Nonnull E enumConstant) {
        Preconditions.checkNotNull(enumConstant, "the enum constant may not be null");

        try {
            ResourceBundle bundle = ResourceBundles.internalForClass(enumConstant.getClass(), locale);
            return bundle.getString(enumConstant.name());
        }
        catch (MissingResourceException e) {
            return missingResourceStrategy.handleMissingResource(enumConstant.name(), e);
        }
    }

    /**
     * Gets the string associated with the given enum constant and the given suffix. The key used to find
     * the string is the enum constant's name, followed by the suffix, separated by a dot. For example, if
     * the enum constant is <code>FOO</code> and the suffix is <code>description</code>, the key is
     * <code>FOO.description</code>. If the suffix already starts with a dot, a new dot is not added as a
     * separator, so the suffix <code>.description</code> has the same effect as the suffix <code>description</code>.
     * @param enumConstant the enum constant for which a String is asked.
     * @param suffix the suffix
     * @return the value associated with the key, or the result of the {@link MissingResourceStrategy}
     * @throws IllegalStateException if the resource is missing and the strategy is the default one
     */
    public <E extends Enum<E>> String getString(@Nonnull E enumConstant, @Nonnull String suffix) {
        Preconditions.checkNotNull(enumConstant, "the enum constant may not be null");
        Preconditions.checkNotNull(suffix, "the suffix may not be null");

        if (suffix.charAt(0) != '.') {
            suffix = "." + suffix;
        }

        try {
            ResourceBundle bundle = ResourceBundles.internalForClass(enumConstant.getClass(), locale);
            return bundle.getString(enumConstant.name() + suffix);
        }
        catch (MissingResourceException e) {
            return missingResourceStrategy.handleMissingResource(enumConstant.name(), e);
        }
    }

    /**
     * Shortcut method for <code>EnumResources.getInstance().getString()</code>
     * @param enumConstant the enum constant for which a String is asked.
     * @return the value associated with the key, or the result of the {@link MissingResourceStrategy}
     * @throws IllegalStateException if the resource is missing
     *
     * @see #getString(Enum)
     */
    public static <E extends Enum<E>> String get(@Nonnull E enumConstant) throws IllegalStateException {
        return EnumResources.getInstance().getString(enumConstant);
    }

    /**
     * Shortcut method for <code>EnumResources.getInstance().getString()</code>
     * @param enumConstant the enum constant for which a String is asked.
     * @param suffix the suffix
     * @return the value associated with the key, or the result of the {@link MissingResourceStrategy}
     * @throws IllegalStateException if the resource is missing
     *
     * @see #getString(Enum, String)
     */
    public static <E extends Enum<E>> String get(@Nonnull E enumConstant, String suffix) throws IllegalStateException {
        return EnumResources.getInstance().getString(enumConstant, suffix);
    }
}
