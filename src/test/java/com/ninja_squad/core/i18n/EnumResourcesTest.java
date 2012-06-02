package com.ninja_squad.core.i18n;

import static org.junit.Assert.*;

import java.util.Comparator;
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
        assertEquals("The default foo description",
                     EnumResources.getInstance().withSuffix("description").getString(TestEnum.FOO));
        assertEquals("The default foo description",
                     EnumResources.getInstance().withSuffix(".description").getString(TestEnum.FOO));
    }

    @Test
    public void getStringWorksWithInnerEnum() {
        assertEquals("The default inner foo label",
                     EnumResources.getInstance().getString(InnerTestEnum.INNER_FOO));
        assertEquals("The default inner foo description",
                     EnumResources.getInstance().withSuffix("description").getString(InnerTestEnum.INNER_FOO));
        assertEquals("The default inner foo description",
                     EnumResources.getInstance().withSuffix(".description").getString(InnerTestEnum.INNER_FOO));
    }

    @Test
    public void getStringReturnsEmptyStringWhenEnumConstantIsNull() {
        assertEquals("", EnumResources.getInstance().<TestEnum>getString(null));
    }

    @Test(expected = IllegalStateException.class)
    public void getStringThrowsExceptionWhenNoBundle() {
        EnumResources.getInstance().getString(InnerTestEnum2.INNER_FOO);
    }

    @Test(expected = IllegalStateException.class)
    public void getStringThrowsExceptionWhenNoKey() {
        EnumResources.getInstance().getString(TestEnum.BAR);
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

    @Test
    public void comparatorWorks() {
        Comparator<TestEnum> comparator = EnumResources.getInstance()
                                                       .withSuffix("forComparator")
                                                       .comparator();
        assertTrue(comparator.compare(TestEnum.FOO, TestEnum.BAR) < 0); // "Hello" < "World"
        assertEquals(0, comparator.compare(TestEnum.FOO, TestEnum.FOO));
    }
}
