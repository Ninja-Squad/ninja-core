package com.ninja_squad.core.i18n;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;


public class EnumResourcesTest {

    enum InnerTestEnum {
        INNER_FOO,
        INNER_BAR;
    }

    enum InnerTestEnum2 {
        INNER_FOO,
        INNER_BAR;
    }

    @Test
    public void getWorks() {
        assertEquals("The default foo label", EnumResources.get(TestEnum.FOO));
        assertEquals("The default foo description", EnumResources.get(TestEnum.FOO, "description"));
    }

    @Test
    public void getStringWorksWithTopLevelEnum() {
        assertEquals("The default foo label", EnumResources.getInstance().withLocale(Locale.ENGLISH).getString(TestEnum.FOO));
        assertEquals("The default foo label", EnumResources.getInstance().getString(TestEnum.FOO));
        assertEquals("The default foo description", EnumResources.getInstance().getString(TestEnum.FOO, "description"));
        assertEquals("The default foo description", EnumResources.getInstance().getString(TestEnum.FOO, ".description"));
    }

    @Test
    public void getStringWorksWithInnerEnum() {
        assertEquals("The default inner foo label", EnumResources.getInstance().getString(InnerTestEnum.INNER_FOO));
        assertEquals("The default inner foo description", EnumResources.getInstance().getString(InnerTestEnum.INNER_FOO, "description"));
        assertEquals("The default inner foo description", EnumResources.getInstance().getString(InnerTestEnum.INNER_FOO, ".description"));
    }

    @Test(expected = IllegalStateException.class)
    public void getStringThrowsExceptionWhenNoBundle() {
        EnumResources.getInstance().getString(InnerTestEnum2.INNER_FOO);
    }

    @Test(expected = IllegalStateException.class)
    public void getStringWithSuffixThrowsExceptionWhenNoBundle() {
        EnumResources.getInstance().getString(InnerTestEnum2.INNER_FOO, "description");
    }

    @Test(expected = IllegalStateException.class)
    public void getStringThrowsExceptionWhenNoKey() {
        EnumResources.getInstance().getString(TestEnum.BAR);
    }

    @Test(expected = IllegalStateException.class)
    public void getStringWithSuffixThrowsExceptionWhenNoKey() {
        EnumResources.getInstance().getString(TestEnum.BAR, "description");
    }

    @Test
    public void getStringReturnsNullWithReturnNullStrategy() {
        assertNull(EnumResources.getInstance().withMissingResourceStrategy(MissingResourceStrategies.RETURN_NULL).getString(TestEnum.BAR));
    }

    @Test
    public void getStringReturnsKeyWithReturnKeyStrategy() {
        assertEquals("BAR", EnumResources.getInstance().withMissingResourceStrategy(MissingResourceStrategies.RETURN_KEY).getString(TestEnum.BAR));
    }

    @Test
    public void getStringReturnsKeyWithQuestionMarksWithJstlStrategy() {
        assertEquals("???BAR???", EnumResources.getInstance().withMissingResourceStrategy(MissingResourceStrategies.JSTL).getString(TestEnum.BAR));
    }
}
