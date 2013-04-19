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

package com.ninja_squad.core.jdbc;

import com.google.common.collect.ImmutableMap;
import com.ninja_squad.core.exception.ShouldNeverHappenException;
import com.ninja_squad.core.proxy.AbstractInvocationHandler;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * The invocation handler used by {@link PreparedStatements#enhance(PreparedStatement)}.
 * @author JB Nizet
 */
class EnhancedPreparedStatementInvocationHandler extends AbstractInvocationHandler {

    private static final Map<Method, Invoker> INVOKERS = buildInvokers();

    private final PreparedStatement delegate;

    EnhancedPreparedStatementInvocationHandler(@Nonnull PreparedStatement delegate) {
        this.delegate = delegate;
    }

    private static Map<Method, Invoker> buildInvokers() {
        try {
            ImmutableMap.Builder<Method, Invoker> builder = ImmutableMap.builder();
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setNullableInt", int.class, Integer.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setNullableInt(delegate, (Integer) args[0], (Integer) args[1]);
                    }
                });
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setNullableLong", int.class, Long.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setNullableLong(delegate, (Integer) args[0], (Long) args[1]);
                    }
                });
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setNullableBoolean", int.class, Boolean.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setNullableBoolean(delegate, (Integer) args[0], (Boolean) args[1]);
                    }
                });
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setNullableDouble", int.class, Double.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setNullableDouble(delegate, (Integer) args[0], (Double) args[1]);
                    }
                });
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setNullableFloat", int.class, Float.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setNullableFloat(delegate, (Integer) args[0], (Float) args[1]);
                    }
                });
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setNullableByte", int.class, Byte.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setNullableByte(delegate, (Integer) args[0], (Byte) args[1]);
                    }
                });
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setNullableShort", int.class, Short.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setNullableShort(delegate, (Integer) args[0], (Short) args[1]);
                    }
                });
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setEnumAsName", int.class, Enum.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setEnumAsName(delegate, (Integer) args[0], (Enum<?>) args[1]);
                    }
                });
            builder.put(
                EnhancedPreparedStatement.class.getDeclaredMethod("setEnumAsOrdinal", int.class, Enum.class),
                new Invoker() {
                    @Override
                    public void invoke(PreparedStatement delegate, Object[] args) throws SQLException {
                        PreparedStatements.setEnumAsOrdinal(delegate, (Integer) args[0], (Enum<?>) args[1]);
                    }
                });

            return builder.build();
        }
        catch (NoSuchMethodException e) {
            throw new ShouldNeverHappenException(e);
        }
    }

    @Override
    public Object doInvoke(Object proxy, Method method, Object[] args) throws SQLException,
                                                                              IllegalArgumentException,
                                                                              IllegalAccessException,
                                                                              InvocationTargetException {
        Invoker invoker = INVOKERS.get(method);
        if (invoker != null) {
            invoker.invoke(delegate, args);
            return null;
        }
        else {
            return method.invoke(delegate, args);
        }
    }

    /**
     * The interface of the invokers that call the static {@link PreparedStatements} methods.
     * @author JB Nizet
     */
    private interface Invoker {
        void invoke(PreparedStatement delegate, Object[] args) throws SQLException;
    }
}
