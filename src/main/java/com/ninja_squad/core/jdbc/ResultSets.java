package com.ninja_squad.core.jdbc;

import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

/**
 * Utility class used to deal with JDBC {@linkplain ResultSet ResultSets}.
 * @author JB
 */
public final class ResultSets {
    private ResultSets() {
    }

    /**
     * Returns an instance of {@link EnhancedResultSet} which is a proxy to the given ResultSet.
     * @param resultSet the ResultSet to proxy
     * @return a proxy to the ResultSet, which offeresultSet the additional methods dealing with nullable
     * values. This proxy calls the static methods in this class to implement these additional
     * methods.
     */
    public static EnhancedResultSet enhance(@Nonnull ResultSet resultSet) {
        Preconditions.checkNotNull(resultSet, "resultSet may not be null");
        return (EnhancedResultSet) Proxy.newProxyInstance(resultSet.getClass().getClassLoader(),
                                                          new Class<?>[] {EnhancedResultSet.class},
                                                          new EnhancedResultSetInvocationHandler(resultSet));
    }

    /**
     * Returns the result of {@link ResultSet#getInt(int)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Integer getNullableInt(@Nonnull ResultSet resultSet,
                                         int columnIndex) throws SQLException {
        int result = resultSet.getInt(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getInt(String)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Integer getNullableInt(@Nonnull ResultSet resultSet,
                                         @Nonnull String columnLabel) throws SQLException {
        int result = resultSet.getInt(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getLong(int)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Long getNullableLong(ResultSet resultSet, int columnIndex) throws SQLException {
        long result = resultSet.getLong(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }


    /**
     * Returns the result of {@link ResultSet#getLong(String)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Long getNullableLong(ResultSet resultSet, String columnLabel) throws SQLException {
        long result = resultSet.getLong(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getBoolean(int)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
        justification = "Returning a nullable boolean is the whole point of this method")
    public static Boolean getNullableBoolean(ResultSet resultSet, int columnIndex) throws SQLException {
        boolean result = resultSet.getBoolean(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getBoolean(String)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
        justification = "Returning a nullable boolean is the whole point of this method")
    public static Boolean getNullableBoolean(ResultSet resultSet, String columnLabel) throws SQLException {
        boolean result = resultSet.getBoolean(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getByte(int)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Byte getNullableByte(ResultSet resultSet, int columnIndex) throws SQLException {
        byte result = resultSet.getByte(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getByte(String)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Byte getNullableByte(ResultSet resultSet, String columnLabel) throws SQLException {
        byte result = resultSet.getByte(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getDouble(int)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Double getNullableDouble(ResultSet resultSet, int columnIndex) throws SQLException {
        double result = resultSet.getDouble(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getDouble(String)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Double getNullableDouble(ResultSet resultSet, String columnLabel) throws SQLException {
        double result = resultSet.getDouble(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getFloat(int)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Float getNullableFloat(ResultSet resultSet, int columnIndex) throws SQLException {
        float result = resultSet.getFloat(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getFloat(String)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Float getNullableFloat(ResultSet resultSet, String columnLabel) throws SQLException {
        float result = resultSet.getFloat(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getShort(int)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Short getNullableShort(ResultSet resultSet, int columnIndex) throws SQLException {
        short result = resultSet.getShort(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Returns the result of {@link ResultSet#getShort(String)}, or <code>null</code> if the value was null
     * according to {@link ResultSet#wasNull()}.
     */
    public static Short getNullableShort(ResultSet resultSet, String columnLabel) throws SQLException {
        short result = resultSet.getShort(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }

    /**
     * Gets the value at the given index as an enum of the given enum class. This method
     * expects the column to hold the name of one of the enum constants.
     * @param resultSet the ResultSet to get the enum from
     * @param columnIndex the index of the column
     * @param enumClass the class of the enum to return
     * @return the enum, or <code>null</code> if the column is <code>NULL</code>
     * @throws SQLException if getting the name fails
     * @throws IllegalStateException if the name is not a valid name for this enum class
     */
    public static <E extends Enum<E>> E getEnumFromName(ResultSet resultSet,
                                                        int columnIndex,
                                                        Class<E> enumClass) throws SQLException {
        String name = resultSet.getString(columnIndex);
        if (name == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, name);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalStateException("The name "
                                                + name
                                                + " is not a valid name for the enum "
                                                + enumClass,
                                            e);
        }
    }

    /**
     * Gets the value at the given index as an enum of the given enum class. This method
     * expects the column to hold the name of one of the enum constants.
     * @param resultSet the ResultSet to get the enum from
     * @param columnLabel the label of the column
     * @param enumClass the class of the enum to return
     * @return the enum, or <code>null</code> if the column is <code>NULL</code>
     * @throws SQLException if getting the name fails
     * @throws IllegalStateException if the name is not a valid name for this enum class
     */
    public static <E extends Enum<E>> E getEnumFromName(ResultSet resultSet,
                                                        String columnLabel,
                                                        Class<E> enumClass) throws SQLException {
        String name = resultSet.getString(columnLabel);
        if (name == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, name);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalStateException("The name " + name + " is not a valid name for the enum " + enumClass, e);
        }
    }

    /**
     * Gets the value at the given index as an enum of the given enum class. This method
     * expects the column to hold the ordinal of one of the enum constants.
     * @param resultSet the ResultSet to get the enum from
     * @param columnIndex the index of the column
     * @param enumClass the class of the enum to return
     * @return the enum, or <code>null</code> if the column is <code>NULL</code>
     * @throws SQLException if getting the ordinal fails
     * @throws IllegalStateException if the ordinal is not a valid ordinal for this enum class
     */
    public static <E extends Enum<E>> E getEnumFromOrdinal(ResultSet resultSet,
                                                           int columnIndex,
                                                           Class<E> enumClass) throws SQLException {
        Integer ordinal = getNullableInt(resultSet, columnIndex);
        if (ordinal == null) {
            return null;
        }
        try {
            return Iterables.get(EnumSet.allOf(enumClass), ordinal);
        }
        catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("The ordinal "
                                                + ordinal
                                                + " is not a valid ordinal for the enum "
                                                + enumClass,
                                            e);
        }
    }

    /**
     * Gets the value at the given index as an enum of the given enum class. This method
     * expects the column to hold the ordinal of one of the enum constants.
     * @param resultSet the ResultSet to get the enum from
     * @param columnLabel the label of the column
     * @param enumClass the class of the enum to return
     * @return the enum, or <code>null</code> if the column is <code>NULL</code>
     * @throws SQLException if getting the ordinal fails
     * @throws IllegalStateException if the ordinal is not a valid ordinal for this enum class
     */
    public static <E extends Enum<E>> E getEnumFromOrdinal(ResultSet resultSet,
                                                           String columnLabel,
                                                           Class<E> enumClass) throws SQLException {
        Integer ordinal = getNullableInt(resultSet, columnLabel);
        if (ordinal == null) {
            return null;
        }
        try {
            return Iterables.get(EnumSet.allOf(enumClass), ordinal);
        }
        catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("The ordinal "
                                                + ordinal
                                                + " is not a valid ordinal for the enum "
                                                + enumClass,
                                            e);
        }
    }
}
