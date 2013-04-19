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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Base class for dynamic proxy invocation handlers. It implements the <code>invoke()</code> method
 * by delegating to {@link #doInvoke(Object, java.lang.reflect.Method, Object[])} in case of a normal,
 * interface method. In case of an Object method (<code>equals()</code>, <code>hashCode()</code>,
 * or <code>toString()</code>), it delegates to the corresponding overridable method, which by default
 * have a correct implementation.
 * @author JB
 */
public abstract class AbstractInvocationHandler implements InvocationHandler {

    /**
     * Template method which delegates to {@link #doInvoke(Object, java.lang.reflect.Method, Object[])},
     * {@link #doEquals(Object, Object)}, {@link #doHashCode(Object)} or {@link #doToString(Object)} depending on
     * the method called on the proxy.
     * @see InvocationHandler#invoke(Object, java.lang.reflect.Method, Object[])
     */
    @Override
    public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            String methodName = method.getName();
            if (methodName.equals("hashCode"))  {
                return doHashCode(proxy);
            }
            else if (methodName.equals("equals")) {
                return doEquals(proxy, args[0]);
            }
            else if (methodName.equals("toString")) {
                return doToString(proxy);
            }
            else {
                throw new IllegalStateException("Unexpected Object method invocation : " + methodName);
            }
        }
        return doInvoke(proxy, method, args);
    }

    /**
     * Method invoked when a real interface method (i.e. not <code>Object.equals()</code>,
     * <code>Object.hashCode()</code> and <code>Object.toString()</code>) is called on the proxy.
     * @see InvocationHandler#invoke(Object, java.lang.reflect.Method, Object[])
     */
    protected abstract Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable;

    /**
     * Method invoked when the <code>Object.hashCode()</code> method is called on the proxy. The implementation returns
     * the system identity hashCode of the proxy (i.e. the same value as what the default
     * <code>Object.hashCode()</code> method returns). Subclasses should override this method and the
     * {@link #doEquals(Object, Object)} method to provide a functional equality.
     * @return the system identity hashCode of the proxy.
     */
    protected int doHashCode(Object proxy) {
        return System.identityHashCode(proxy);
    }

    /**
     * Method invoked when the <code>Object.equals()</code> method is called on the proxy. The implementation returns
     * true only if the argument of the equals method is the same (<code>==</code>) (i.e. the same value as what the
     * default <code>Object.equals()</code> method returns). Subclasses should override this method and the
     * {@link #doHashCode(Object)} method to provide a functional equality.
     * @param proxy the proxy on which equals() is called
     * @param other the argument of the equals() method call
     * @return true if <code>proxy == other</code>, <code>false</code> otherwise
     */
    protected boolean doEquals(Object proxy, Object other) {
        return proxy == other;
    }

    /**
     * Method invoked when the <code>Object.toString()</code> method is called on the proxy. The implementation returns
     * the class name of the proxy followed by '@' and its system identity hashCode in hexadecimal. Subclasses should
     * override this method to provide a functional <code>toString()</code>.
     * @param proxy the proxy on which toString() is called
     * @return true if <code>proxy == other</code>, <code>false</code> otherwise
     */
    protected String doToString(Object proxy) {
        return proxy.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(proxy));
    }
}
