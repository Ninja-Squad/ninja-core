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

package com.ninja_squad.core.retry;

import java.util.concurrent.ExecutionException;

/**
 * An attempt of a call, which resulted either in a result returned by the call,
 * or in a Throwable thrown by the call.
 * @author JB Nizet
 *
 * @param <V> The type returned by the wrapped callable.
 */
public interface Attempt<V> {

    /**
     * Returns the result of the attempt, if any.
     * @return the result of the attempt
     * @throws ExecutionException if an exception was thrown by the attempt. The thrown
     * exception is set as the cause of the ExecutionException
     */
    V get() throws ExecutionException;

    /**
     * Tells if the call returned a result or not
     * @return <code>true</code> if the call returned a result, <code>false</code>
     * if it threw an exception
     */
    boolean hasResult();

    /**
     * Tells if the call threw an exception or not
     * @return <code>true</code> if the call threw an exception, <code>false</code>
     * if it returned a result
     */
    boolean hasException();

    /**
     * Gets the result of the call
     * @return the result of the call
     * @throws IllegalStateException if the call didn't return a result, but threw an exception,
     * as indicated by {@link #hasResult()}
     */
    V getResult() throws IllegalStateException;

    /**
     * Gets the exception thrown by the call
     * @return the exception thrown by the call
     * @throws IllegalStateException if the call didn't throw an exception,
     * as indicated by {@link #hasException()}
     */
    Throwable getExceptionCause() throws IllegalStateException;
}
