/*
 * The MIT License
 *
 * Copyright (c) 2012, Ninja Squad
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ninja_squad.core.proxy;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AbstractInvocationHandlerTest {
    @Test
    public void invokeShouldDispatchToTheRightMethod() throws Exception {
        final Foo mockFoo = mock(Foo.class);
        AbstractInvocationHandler handler = new AbstractInvocationHandler() {
            @Override
            protected Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
                return "hello";
            }

            @Override
            protected int doHashCode(Object proxy) {
                return 1111;
            }

            @Override
            protected boolean doEquals(Object proxy, Object other) {
                mockFoo.sayHello();
                return false;
            }

            @Override
            protected String doToString(Object proxy) {
                return "What?";
            }
        };
        Foo foo = (Foo) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] {Foo.class}, handler);

        assertEquals("hello", foo.sayHello());
        assertFalse(foo.equals(foo));
        assertEquals(1111, foo.hashCode());
        assertEquals("What?", foo.toString());
        verify(mockFoo).sayHello();
    }

    @Test
    public void defaultEqualsShouldWork() {
        AbstractInvocationHandler handler = new AbstractInvocationHandler() {
            @Override
            protected Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
                return "hello";
            }
        };
        Foo foo1 = (Foo) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] {Foo.class}, handler);
        Foo foo2 = (Foo) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] {Foo.class}, handler);
        assertEquals(foo1, foo1);
        assertNotEquals(foo1, foo2);
    }

    @Test
    public void defaultHashCodeShouldWork() {
        AbstractInvocationHandler handler = new AbstractInvocationHandler() {
            @Override
            protected Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
                return "hello";
            }
        };
        Foo foo1 = (Foo) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] {Foo.class}, handler);
        Foo foo2 = (Foo) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] {Foo.class}, handler);
        assertEquals(foo1.hashCode(), foo1.hashCode());
        assertNotEquals(foo1.hashCode(), foo2.hashCode());
    }

    @Test
    public void defaultToStringShouldWork() {
        AbstractInvocationHandler handler = new AbstractInvocationHandler() {
            @Override
            protected Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
                return "hello";
            }
        };
        Foo foo = (Foo) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] {Foo.class}, handler);
        assertNotNull(foo.toString());
        assertTrue(foo.toString().contains("@"));
    }

    private interface Foo {
        String sayHello();
    }
}
