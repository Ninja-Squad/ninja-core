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

import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

/**
 * Utility class used to deal with JDBC {@linkplain PreparedStatement PreparedStatements}.
 * @author JB Nizet
 */
public final class PreparedStatements {
    private PreparedStatements() {
    }

    /**
     * Returns an instance of {@link EnhancedPreparedStatement} which is a proxy to the given PreparedStatement.
     * Note that if the given statement is already enhanced, it's not enhanced a second time, but returned directly.
     * @param statement the PreparedStatement to proxy
     * @return a proxy to the PreparedStatement, which offeres the additional methods dealing with nullable
     * values. This proxy calls the static methods in this class to implement these additional methods.
     */
    public static EnhancedPreparedStatement enhance(@Nonnull PreparedStatement statement) {
        Preconditions.checkNotNull(statement, "statement may not be null");
        if (statement instanceof EnhancedPreparedStatement) {
            return (EnhancedPreparedStatement) statement;
        }
        return (EnhancedPreparedStatement) Proxy.newProxyInstance(
            statement.getClass().getClassLoader(),
            new Class<?>[] {EnhancedPreparedStatement.class},
            new EnhancedPreparedStatementInvocationHandler(statement));
    }

    /**
     * Binds a nullable integer to the statement. Uses the {@link PreparedStatement#setNull(int, int)} with the
     * SQL INTEGER type if the value if <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setNullableInt(@Nonnull PreparedStatement statement,
                                      int parameterIndex,
                                      @Nullable Integer value) throws SQLException {
        if (value != null) {
            statement.setInt(parameterIndex, value);
        }
        else {
            statement.setNull(parameterIndex, Types.INTEGER);
        }
    }

    /**
     * Binds a nullable long to the statement. Uses the {@link PreparedStatement#setNull(int, int)} with the
     * SQL BIGINT type if the value if <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setNullableLong(@Nonnull PreparedStatement statement,
                                       int parameterIndex,
                                       @Nullable Long value) throws SQLException {
        if (value != null) {
            statement.setLong(parameterIndex, value);
        }
        else {
            statement.setNull(parameterIndex, Types.BIGINT);
        }
    }

    /**
     * Binds a nullable boolean to the statement. Uses the {@link PreparedStatement#setNull(int, int)} with the
     * SQL BOOLEAN type if the value if <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setNullableBoolean(@Nonnull PreparedStatement statement,
                                          int parameterIndex,
                                          @Nullable Boolean value) throws SQLException {
        if (value != null) {
            statement.setBoolean(parameterIndex, value);
        }
        else {
            statement.setNull(parameterIndex, Types.BOOLEAN);
        }
    }

    /**
     * Binds a nullable byte to the statement. Uses the {@link PreparedStatement#setNull(int, int)} with the
     * SQL TINYINT type if the value if <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setNullableByte(@Nonnull PreparedStatement statement,
                                       int parameterIndex,
                                       @Nullable Byte value) throws SQLException {
        if (value != null) {
            statement.setByte(parameterIndex, value);
        }
        else {
            statement.setNull(parameterIndex, Types.TINYINT);
        }
    }

    /**
     * Binds a nullable double to the statement. Uses the {@link PreparedStatement#setNull(int, int)} with the
     * SQL DOUBLE type if the value if <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setNullableDouble(@Nonnull PreparedStatement statement,
                                         int parameterIndex,
                                         @Nullable Double value) throws SQLException {
        if (value != null) {
            statement.setDouble(parameterIndex, value);
        }
        else {
            statement.setNull(parameterIndex, Types.DOUBLE);
        }
    }

    /**
     * Binds a nullable float to the statement. Uses the {@link PreparedStatement#setNull(int, int)} with the
     * SQL REAL type if the value if <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setNullableFloat(@Nonnull PreparedStatement statement,
                                        int parameterIndex,
                                        @Nullable Float value) throws SQLException {
        if (value != null) {
            statement.setFloat(parameterIndex, value);
        }
        else {
            statement.setNull(parameterIndex, Types.REAL);
        }
    }

    /**
     * Binds a nullable short to the statement. Uses the {@link PreparedStatement#setNull(int, int)} with the
     * SQL SMALLINT type if the value if <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setNullableShort(@Nonnull PreparedStatement statement,
                                        int parameterIndex,
                                        @Nullable Short value) throws SQLException {
        if (value != null) {
            statement.setShort(parameterIndex, value);
        }
        else {
            statement.setNull(parameterIndex, Types.SMALLINT);
        }
    }

    /**
     * Binds the name of an enum instance as a String to the statement, or null if the enum is <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setEnumAsName(@Nonnull PreparedStatement statement,
                                     int parameterIndex,
                                     @Nullable Enum<?> value) throws SQLException {
        if (value != null) {
            statement.setString(parameterIndex, value.name());
        }
        else {
            statement.setString(parameterIndex, null);
        }
    }

    /**
     * Binds the ordinal of an enum instance as an int to the statement, or <code>null</code> if the enum is
     * <code>null</code>.
     * @param statement the statement to which the parameter must be bound
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    public static void setEnumAsOrdinal(@Nonnull PreparedStatement statement,
                                        int parameterIndex,
                                        @Nullable Enum<?> value) throws SQLException {
        if (value != null) {
            statement.setInt(parameterIndex, value.ordinal());
        }
        else {
            statement.setNull(parameterIndex, Types.INTEGER);
        }
    }
}
