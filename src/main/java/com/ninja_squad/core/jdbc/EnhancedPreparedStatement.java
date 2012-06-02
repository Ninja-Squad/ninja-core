package com.ninja_squad.core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A PreparedStatement interface that allows binding nullable primitives easily, as well as enums.<br/>
 * This interface adds <code>setNullableXxx()</code> methods to the standard <code>setXxx()</code>
 * methods, which take the object wrapper, or <code>null</code> in case the value is <code>null</code>
 *
 * @author JB
 */
public interface EnhancedPreparedStatement extends PreparedStatement {

    /**
     * Binds a nullable int value to the prepared statement.
     * @see PreparedStatements#setNullableInt(PreparedStatement, int, Integer) for how this method works if the value
     * is <code>null</code>
     */
    void setNullableInt(int parameterIndex, Integer value) throws SQLException;

    /**
     * Binds a nullable long value to the prepared statement.
     * @see PreparedStatements#setNullableLong(PreparedStatement, int, Long) for how this method works if the value
     * is <code>null</code>
     */
    void setNullableLong(int parameterIndex, Long value) throws SQLException;

    /**
     * Binds a nullable boolean value to the prepared statement.
     * @see PreparedStatements#setNullableBoolean(PreparedStatement, int, Boolean) for how this method works if the
     * value is <code>null</code>
     */
    void setNullableBoolean(int parameterIndex, Boolean value) throws SQLException;

    /**
     * Binds a nullable byte value to the prepared statement.
     * @see PreparedStatements#setNullableByte(PreparedStatement, int, Byte) for how this method works if the
     * value is <code>null</code>
     */
    void setNullableByte(int parameterIndex, Byte value) throws SQLException;


    /**
     * Binds a nullable double value to the prepared statement.
     * @see PreparedStatements#setNullableDouble(PreparedStatement, int, Double) for how this method works if the
     * value is <code>null</code>
     */
    void setNullableDouble(int parameterIndex, Double value) throws SQLException;

    /**
     * Binds a nullable float value to the prepared statement.
     * @see PreparedStatements#setNullableFloat(PreparedStatement, int, Float) for how this method works if the
     * value is <code>null</code>
     */
    void setNullableFloat(int parameterIndex, Float value) throws SQLException;

    /**
     * Binds a nullable short value to the prepared statement.
     * @see PreparedStatements#setNullableShort(PreparedStatement, int, Short) for how this method works if the
     * value is <code>null</code>
     */
    void setNullableShort(int parameterIndex, Short value) throws SQLException;

    /**
     * Binds the name of an enum instance as a String to the statement, or null if the enum is <code>null</code>.
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    void setEnumAsName(int parameterIndex, Enum<?> value) throws SQLException;

    /**
     * Binds the ordinal of an enum instance as an int to the statement, or <code>null</code> if the enum is
     * <code>null</code>.
     * @param parameterIndex the index of the parameter to bind
     * @param value the value to bind
     * @throws SQLException if the value can't be bound
     */
    void setEnumAsOrdinal(int parameterIndex, Enum<?> value) throws SQLException;
}
