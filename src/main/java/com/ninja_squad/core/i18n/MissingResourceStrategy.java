package com.ninja_squad.core.i18n;

import java.util.MissingResourceException;

import javax.annotation.Nonnull;

/**
 * Strategy used to handle missing resources (i.e. the resource bundle is not found,
 * or the key is not found in the resource bundle)
 * @author JB
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
