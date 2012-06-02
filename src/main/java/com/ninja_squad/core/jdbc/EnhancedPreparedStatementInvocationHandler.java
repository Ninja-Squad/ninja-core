package com.ninja_squad.core.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableMap;
import com.ninja_squad.core.exception.ShouldNeverHappenException;

/**
 * The invocation handler used by {@link PreparedStatements#enhance(PreparedStatement)}.
 * @author JB
 */
class EnhancedPreparedStatementInvocationHandler implements InvocationHandler {

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
    public Object invoke(Object proxy, Method method, Object[] args) throws SQLException,
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
     * @author JB
     */
    private interface Invoker {
        void invoke(PreparedStatement delegate, Object[] args) throws SQLException;
    }
}
