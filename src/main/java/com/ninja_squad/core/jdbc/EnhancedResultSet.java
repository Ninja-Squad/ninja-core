package com.ninja_squad.core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A ResultSet interface that allows fetching nullable primitives easily, as well as enums. Indeed, the
 * methods returning primitives from a ResultSet return default values (0 or <code>true</code>)
 * if the actual value is <code>NULL</code> in the database row. To know that the value is null,
 * the {@link ResultSet#wasNull()} method must be called, which is cumbersome.<br/>
 * This interface adds <code>getNullableXxx()</code> methods to the standard <code>getXxx()</code>
 * methods, which return the object wrapper, or <code>null</code> in case the value is <code>NULL</code>
 *
 * @author JB
 */
public interface EnhancedResultSet extends ResultSet {

    /**
     * Similar to {@link ResultSet#getInt(int)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Integer getNullableInt(int columnIndex) throws SQLException;

    /**
     * Similar to {@link ResultSet#getInt(String)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Integer getNullableInt(String columnLabel) throws SQLException;

    /**
     * Similar to {@link ResultSet#getLong(int)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Long getNullableLong(int columnIndex) throws SQLException;

    /**
     * Similar to {@link ResultSet#getLong(String)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Long getNullableLong(String columnLabel) throws SQLException;

    /**
     * Similar to {@link ResultSet#getBoolean(int)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Boolean getNullableBoolean(int columnIndex) throws SQLException;

    /**
     * Similar to {@link ResultSet#getBoolean(String)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Boolean getNullableBoolean(String columnLabel) throws SQLException;

    /**
     * Similar to {@link ResultSet#getByte(int)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Byte getNullableByte(int columnIndex) throws SQLException;

    /**
     * Similar to {@link ResultSet#getByte(String)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Byte getNullableByte(String columnLabel) throws SQLException;

    /**
     * Similar to {@link ResultSet#getDouble(int)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Double getNullableDouble(int columnIndex) throws SQLException;

    /**
     * Similar to {@link ResultSet#getDouble(String)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Double getNullableDouble(String columnLabel) throws SQLException;

    /**
     * Similar to {@link ResultSet#getFloat(int)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Float getNullableFloat(int columnIndex) throws SQLException;

    /**
     * Similar to {@link ResultSet#getFloat(String)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Float getNullableFloat(String columnLabel) throws SQLException;

    /**
     * Similar to {@link ResultSet#getShort(int)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Short getNullableShort(int columnIndex) throws SQLException;

    /**
     * Similar to {@link ResultSet#getShort(String)}, but returns <code>null</code>
     * if the value is <code>NULL</code>
     */
    Short getNullableShort(String columnLabel) throws SQLException;

    /**
     * Gets the value at the given index as an enum of the given enum class. This method
     * expects the column to hold the name of one of the enum constants.
     * @param columnIndex the index of the column
     * @param enumClass the class of the enum to return
     * @return the enum, or <code>null</code> if the column is <code>NULL</code>
     * @throws SQLException if getting the name fails
     * @throws IllegalStateException if the name is not a valid name for this enum class
     */
    <E extends Enum<E>> E getEnumFromName(int columnIndex, Class<E> enumClass) throws SQLException;

    /**
     * Gets the value at the given index as an enum of the given enum class. This method
     * expects the column to hold the name of one of the enum constants.
     * @param columnLabel the label of the column
     * @param enumClass the class of the enum to return
     * @return the enum, or <code>null</code> if the column is <code>NULL</code>
     * @throws SQLException if getting the name fails
     * @throws IllegalStateException if the name is not a valid name for this enum class
     */
    <E extends Enum<E>> E getEnumFromName(String columnLabel, Class<E> enumClass) throws SQLException;

    /**
     * Gets the value at the given index as an enum of the given enum class. This method
     * expects the column to hold the ordinal of one of the enum constants.
     * @param columnIndex the index of the column
     * @param enumClass the class of the enum to return
     * @return the enum, or <code>null</code> if the column is <code>NULL</code>
     * @throws SQLException if getting the ordinal fails
     * @throws IllegalStateException if the ordinal is not a valid ordinal for this enum class
     */
    <E extends Enum<E>> E getEnumFromOrdinal(int columnIndex, Class<E> enumClass) throws SQLException;

    /**
     * Gets the value at the given index as an enum of the given enum class. This method
     * expects the column to hold the ordinal of one of the enum constants.
     * @param columnLabel the label of the column
     * @param enumClass the class of the enum to return
     * @return the enum, or <code>null</code> if the column is <code>NULL</code>
     * @throws SQLException if getting the ordinal fails
     * @throws IllegalStateException if the ordinal is not a valid ordinal for this enum class
     */
    <E extends Enum<E>> E getEnumFromOrdinal(String columnLabel, Class<E> enumClass) throws SQLException;
}
