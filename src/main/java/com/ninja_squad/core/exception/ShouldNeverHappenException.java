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

package com.ninja_squad.core.exception;

/**
 * This exception indicates that something happened that should never happen. It's useful when
 * a method calls a method that throws a checked exception, but we know that this exception
 * can (or at least should, unless there is a bug) in fact never be thrown. It's also a good idea
 * to throw such an exception in a default case of a switch or if/else statement, if this default
 * case should never happen.
 * @author JB Nizet
 *
 */
public class ShouldNeverHappenException extends IllegalStateException {

    private static final String DEFAULT_MESSAGE = "Something that should never happen has happened.";

    /**
     * Default constructor, using a default message
     */
    public ShouldNeverHappenException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructor, using the given message and chaining the given Throwable cause
     */
    public ShouldNeverHappenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor, using the given message
     */
    public ShouldNeverHappenException(String message) {
        super(message);
    }

    /**
     * Constructor, using the default message, and chaining the given Throwable cause
     */
    public ShouldNeverHappenException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
