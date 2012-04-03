package com.ninja_squad.core.i18n;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;


public class ClassResourcesTest {

    @Test
    public void getStringWorks() {
        assertEquals("The test label", ClassResources.forClass(ClassResourcesTest.class).getString("test"));
        assertEquals("Le libellé en français", ClassResources.forClass(ClassResourcesTest.class)
                                                             .withLocale(new Locale("zz"))
                                                             .getString("test"));
    }

    @Test(expected = IllegalStateException.class)
    public void getStringThrowsExceptionWhenNoBundle() {
        ClassResources.forClass(String.class).getString("test");
    }

    @Test(expected = IllegalStateException.class)
    public void getStringThrowsExceptionWhenNoKey() {
        ClassResources.forClass(ClassResourcesTest.class).getString("doesntExist");
    }

    @Test
    public void getStringReturnsNullWithReturnNullStrategy() {
        assertNull(ClassResources.forClass(ClassResourcesTest.class)
                                 .withMissingResourceStrategy(MissingResourceStrategies.RETURN_NULL)
                                 .getString("doesntExist"));
    }

    @Test
    public void getStringReturnsKeyWithReturnKeyStrategy() {
        assertEquals("doesntExist",
                     ClassResources.forClass(ClassResourcesTest.class)
                                   .withMissingResourceStrategy(MissingResourceStrategies.RETURN_KEY)
                                   .getString("doesntExist"));
    }

    @Test
    public void getStringReturnsKeyWithQuestionMarksWithJstlStrategy() {
        assertEquals("???doesntExist???",
                     ClassResources.forClass(ClassResourcesTest.class)
                                   .withMissingResourceStrategy(MissingResourceStrategies.JSTL)
                                   .getString("doesntExist"));
    }
}
