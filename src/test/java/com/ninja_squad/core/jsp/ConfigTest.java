package com.ninja_squad.core.jsp;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigTest {
    @Test
    public void getAndSetConfigWork() {
        assertSame(JstlLocaleResolver.INSTANCE, Config.getLocaleResolver());
        try {
            Config.setLocaleResolver(RequestLocaleResolver.INSTANCE);
            assertSame(RequestLocaleResolver.INSTANCE, Config.getLocaleResolver());
        }
        finally {
            Config.setLocaleResolver(JstlLocaleResolver.INSTANCE);
        }
    }
}
