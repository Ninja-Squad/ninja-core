package com.ninja_squad.core.i18n;

import java.util.MissingResourceException;

/**
 * Instances of {@link MissingResourceStrategy}
 *
 * @author JB
 */
public final class MissingResourceStrategies {

    /**
     * A {@link MissingResourceStrategy} consisting in returning <code>null</code> when
     * the resource is missing
     */
    public static final MissingResourceStrategy RETURN_NULL = new MissingResourceStrategy() {
        @Override
        public String handleMissingResource(String key, MissingResourceException e) {
            return null;
        }
    };

    /**
     * A {@link MissingResourceStrategy} consisting in returning mimicking the behavior of the
     * JSTL when the resource is missing: returning the key surrounded with <code>???</code>
     */
    public static final MissingResourceStrategy JSTL = new MissingResourceStrategy() {
        @Override
        public String handleMissingResource(String key, MissingResourceException e) {
            return "???" + key + "???";
        }
    };

    /**
     * A {@link MissingResourceStrategy} consisting in throwing an IllegalStateException when
     * the resource is missing. This is the default strategy.
     */
    public static final MissingResourceStrategy THROW_EXCEPTION = new MissingResourceStrategy() {
        @Override
        public String handleMissingResource(String key, MissingResourceException e) {
            throw new IllegalStateException("Impossible to find the value for the key " + key, e);
        }
    };

    /**
     * A {@link MissingResourceStrategy} consisting in returning the key when the resource is missing.
     */
    public static final MissingResourceStrategy RETURN_KEY = new MissingResourceStrategy() {
        @Override
        public String handleMissingResource(String key, MissingResourceException e) {
            return key;
        }
    };

    private MissingResourceStrategies() {
    }
}
