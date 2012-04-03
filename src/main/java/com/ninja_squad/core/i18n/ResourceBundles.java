package com.ninja_squad.core.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 * Utility class to used to get a resource bundle for a given class
 * @author JB
 */
public final class ResourceBundles {
    private ResourceBundles() {
    }

    /**
     * Returns the resource bundle associated with the given class.
     * The resource bundle has the same name as the class, but is searched in a sub-package
     * named <em>resources</em> of the package of the class. <br/>
     * So if the class is <code>com.foo.bar.MyClass</code>, the base name of the resource
     * bundle is <code>com.foo.bar.resources.MyClass</code>.<br/>
     * In the case of inner classes, the binary name of the class is used. So
     * if the class is <code>com.foo.bar.resources.MyClass.MyInnerClass</code>, the base name
     * of the resource bundle is <code>com.foo.bar.resources.MyClass$MyInnerClass</code>.
     * @param clazz the class for which a resource bundle is asked.
     * @param locale the locale for which the resource bundle is asked.
     * @return the resource bundle
     * @throws IllegalStateException if the bundle isn't found
     */
    public static ResourceBundle forClass(@Nonnull Class<?> clazz,
                                          @Nonnull Locale locale) throws IllegalStateException {
        Preconditions.checkNotNull(clazz, "the class may not be null");
        Preconditions.checkNotNull(locale, "the locale may not be null");

        String baseName = getBaseNameForClass(clazz);
        try {
            return ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());
        }
        catch (MissingResourceException e) {
            throw new IllegalStateException("Can't find resource bundle for class "
                                            + clazz
                                            + " under base name "
                                            + baseName,
                                            e);
        }
    }

    /**
     * Does the same thing as {@link #forClass(Class, Locale)}, but with the default locale
     * @param clazz the class for which a resource bundle is asked.
     * @return the resource bundle
     * @throws IllegalStateException if the bundle isn't found
     */
    public static ResourceBundle forClass(@Nonnull Class<?> clazz) throws IllegalStateException {
        return forClass(clazz, Locale.getDefault());
    }

    /**
     * For internal use. Same as {@link #forClass(Class, Locale)}, but doesn't throw an
     * IllegalStateException if the resource bundle is not found
     * @throws MissingResourceException if the bundle is not found
     */
    static ResourceBundle internalForClass(@Nonnull Class<?> clazz,
                                           @Nonnull Locale locale) throws MissingResourceException {
        Preconditions.checkNotNull(clazz, "the class may not be null");
        Preconditions.checkNotNull(locale, "the locale may not be null");

        String baseName = getBaseNameForClass(clazz);
        return ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());
    }

    private static String getBaseNameForClass(Class<?> clazz) {
        String className = clazz.getName();
        String packageName = clazz.getPackage().getName();
        String binarySimpleName = className.substring(packageName.length());
        String baseName = packageName + ".resources." + binarySimpleName;
        return baseName;
    }
}
