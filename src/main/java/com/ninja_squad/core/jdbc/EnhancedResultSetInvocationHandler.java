package com.ninja_squad.core.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableMap;
import com.ninja_squad.core.exception.ShouldNeverHappenException;

/**
 * The invocation handler used by {@link ResultSets#enhance(ResultSet)}.
 * @author JB
 */
class EnhancedResultSetInvocationHandler implements InvocationHandler {
    private static final Map<Method, Invoker> INVOKERS = buildInvokers();

    private final ResultSet delegate;

    EnhancedResultSetInvocationHandler(@Nonnull ResultSet delegate) {
        this.delegate = delegate;
    }

    private static Map<Method, Invoker> buildInvokers() {
        try {
            ImmutableMap.Builder<Method, Invoker> builder = ImmutableMap.builder();
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableInt", int.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableInt(delegate, (Integer) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableInt", String.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableInt(delegate, (String) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableLong", int.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableLong(delegate, (Integer) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableLong", String.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableLong(delegate, (String) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableBoolean", int.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableBoolean(delegate, (Integer) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableBoolean", String.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableBoolean(delegate, (String) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableDouble", int.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableDouble(delegate, (Integer) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableDouble", String.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableDouble(delegate, (String) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableFloat", int.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableFloat(delegate, (Integer) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableFloat", String.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableFloat(delegate, (String) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableShort", int.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableShort(delegate, (Integer) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableShort", String.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableShort(delegate, (String) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableByte", int.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableByte(delegate, (Integer) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getNullableByte", String.class),
                new Invoker() {
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getNullableByte(delegate, (String) args[0]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getEnumFromName", int.class, Class.class),
                new Invoker() {
                    @SuppressWarnings({"unchecked", "rawtypes"})
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getEnumFromName(delegate, (Integer) args[0], (Class) args[1]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getEnumFromName", String.class, Class.class),
                new Invoker() {
                    @SuppressWarnings({"unchecked", "rawtypes"})
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getEnumFromName(delegate, (String) args[0], (Class) args[1]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getEnumFromOrdinal", int.class, Class.class),
                new Invoker() {
                    @SuppressWarnings({"unchecked", "rawtypes"})
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getEnumFromOrdinal(delegate, (Integer) args[0], (Class) args[1]);
                    }
                });
            builder.put(
                EnhancedResultSet.class.getDeclaredMethod("getEnumFromOrdinal", String.class, Class.class),
                new Invoker() {
                    @SuppressWarnings({"unchecked", "rawtypes"})
                    @Override
                    public Object invoke(ResultSet delegate, Object[] args) throws SQLException {
                        return ResultSets.getEnumFromOrdinal(delegate, (String) args[0], (Class) args[1]);
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
            return invoker.invoke(delegate, args);
        }
        else {
            return method.invoke(delegate, args);
        }
    }

    /**
     * The interface of the invokers that call the static {@link ResultSets} methods.
     * @author JB
     */
    private interface Invoker {
        Object invoke(ResultSet delegate, Object[] args) throws SQLException;
    }
}
