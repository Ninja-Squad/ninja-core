package com.ninja_squad.core.i18n;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class ResourceBundlesTest {
    @Test
    public void forClassWorks() {
        ResourceBundle bundle = ResourceBundles.forClass(TestEnum.class);
        assertNotNull(bundle.getString("FOO"));

        bundle = ResourceBundles.forClass(TestEnum.class, new Locale("zz"));
        assertEquals("Le libellé français de foo", bundle.getString("FOO"));
    }

    @Test(expected = IllegalStateException.class)
    public void forClassThrowsIllegalStateExceptionWhenNonExistingBundle() {
        ResourceBundles.forClass(this.getClass());
    }
}
