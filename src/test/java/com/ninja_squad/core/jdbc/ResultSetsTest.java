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

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResultSetsTest {
    private enum TestEnum {
        FOO, BAR;
    }

    @Test
    public void enhanceShouldNotEnhanceTwice() {
        ResultSet mockResultSet = mock(ResultSet.class);
        EnhancedResultSet enhanced1 = ResultSets.enhance(mockResultSet);
        EnhancedResultSet enhanced2 = ResultSets.enhance(enhanced1);
        assertSame(enhanced1, enhanced2);
    }

    @Test
    public void getNullableIntWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt(2)).thenReturn(7);

        assertEquals(7, ResultSets.getNullableInt(mockResultSet, 2).intValue());
        assertEquals(7, ResultSets.enhance(mockResultSet).getNullableInt(2).intValue());

        when(mockResultSet.getInt(2)).thenReturn(0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableInt(mockResultSet, 2));
        assertNull(ResultSets.enhance(mockResultSet).getNullableInt(2));
    }

    @Test
    public void getNullableIntWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("foo")).thenReturn(7);

        assertEquals(7, ResultSets.getNullableInt(mockResultSet, "foo").intValue());
        assertEquals(7, ResultSets.enhance(mockResultSet).getNullableInt("foo").intValue());

        when(mockResultSet.getInt("foo")).thenReturn(0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableInt(mockResultSet, "foo"));
        assertNull(ResultSets.enhance(mockResultSet).getNullableInt("foo"));
    }

    @Test
    public void getNullableLongWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong(2)).thenReturn(7L);

        assertEquals(7L, ResultSets.getNullableLong(mockResultSet, 2).longValue());
        assertEquals(7L, ResultSets.enhance(mockResultSet).getNullableLong(2).longValue());

        when(mockResultSet.getLong(2)).thenReturn(0L);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableLong(mockResultSet, 2));
        assertNull(ResultSets.enhance(mockResultSet).getNullableLong(2));
    }

    @Test
    public void getNullableLongWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong("foo")).thenReturn(7L);

        assertEquals(7L, ResultSets.getNullableLong(mockResultSet, "foo").longValue());
        assertEquals(7L, ResultSets.enhance(mockResultSet).getNullableLong("foo").longValue());

        when(mockResultSet.getLong("foo")).thenReturn(0L);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableLong(mockResultSet, "foo"));
        assertNull(ResultSets.enhance(mockResultSet).getNullableLong("foo"));
    }

    @Test
    public void getNullableBooleanWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getBoolean(2)).thenReturn(true);

        assertEquals(true, ResultSets.getNullableBoolean(mockResultSet, 2).booleanValue());
        assertEquals(true, ResultSets.enhance(mockResultSet).getNullableBoolean(2).booleanValue());

        when(mockResultSet.getBoolean(2)).thenReturn(false);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableBoolean(mockResultSet, 2));
        assertNull(ResultSets.enhance(mockResultSet).getNullableBoolean(2));
    }

    @Test
    public void getNullableBooleanWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getBoolean("foo")).thenReturn(true);

        assertEquals(true, ResultSets.getNullableBoolean(mockResultSet, "foo").booleanValue());
        assertEquals(true, ResultSets.enhance(mockResultSet).getNullableBoolean("foo").booleanValue());

        when(mockResultSet.getBoolean("foo")).thenReturn(false);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableBoolean(mockResultSet, "foo"));
        assertNull(ResultSets.enhance(mockResultSet).getNullableBoolean("foo"));
    }

    @Test
    public void getNullableDoubleWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getDouble(2)).thenReturn(7.0);

        assertEquals(7.0, ResultSets.getNullableDouble(mockResultSet, 2).doubleValue(), 0.0001);
        assertEquals(7.0, ResultSets.enhance(mockResultSet).getNullableDouble(2).doubleValue(), 0.0001);

        when(mockResultSet.getDouble(2)).thenReturn(0.0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableDouble(mockResultSet, 2));
        assertNull(ResultSets.enhance(mockResultSet).getNullableDouble(2));
    }

    @Test
    public void getNullableDoubleWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getDouble("foo")).thenReturn(7.0);

        assertEquals(7.0, ResultSets.getNullableDouble(mockResultSet, "foo").doubleValue(), 0.0001);
        assertEquals(7.0, ResultSets.enhance(mockResultSet).getNullableDouble("foo").doubleValue(), 0.0001);

        when(mockResultSet.getDouble("foo")).thenReturn(0.0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableDouble(mockResultSet, "foo"));
        assertNull(ResultSets.enhance(mockResultSet).getNullableDouble("foo"));
    }

    @Test
    public void getNullableFloatWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getFloat(2)).thenReturn(7.0F);

        assertEquals(7.0F, ResultSets.getNullableFloat(mockResultSet, 2).floatValue(), 0.0001);
        assertEquals(7.0F, ResultSets.enhance(mockResultSet).getNullableFloat(2).floatValue(), 0.0001);

        when(mockResultSet.getFloat(2)).thenReturn(0.0F);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableFloat(mockResultSet, 2));
        assertNull(ResultSets.enhance(mockResultSet).getNullableFloat(2));
    }

    @Test
    public void getNullableFloatWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getFloat("foo")).thenReturn(7.0F);

        assertEquals(7.0F, ResultSets.getNullableFloat(mockResultSet, "foo").floatValue(), 0.0001);
        assertEquals(7.0F, ResultSets.enhance(mockResultSet).getNullableFloat("foo").floatValue(), 0.0001);

        when(mockResultSet.getFloat("foo")).thenReturn(0.0F);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableFloat(mockResultSet, "foo"));
        assertNull(ResultSets.enhance(mockResultSet).getNullableFloat("foo"));
    }

    @Test
    public void getNullableShortWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getShort(2)).thenReturn((short) 7);

        assertEquals(7, ResultSets.getNullableShort(mockResultSet, 2).shortValue());
        assertEquals(7, ResultSets.enhance(mockResultSet).getNullableShort(2).shortValue());

        when(mockResultSet.getShort(2)).thenReturn((short) 0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableShort(mockResultSet, 2));
        assertNull(ResultSets.enhance(mockResultSet).getNullableShort(2));
    }

    @Test
    public void getNullableShortWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getShort("foo")).thenReturn((short) 7);

        assertEquals(7, ResultSets.getNullableShort(mockResultSet, "foo").shortValue());
        assertEquals(7, ResultSets.enhance(mockResultSet).getNullableShort("foo").shortValue());

        when(mockResultSet.getShort("foo")).thenReturn((short) 0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableShort(mockResultSet, "foo"));
        assertNull(ResultSets.enhance(mockResultSet).getNullableShort("foo"));
    }

    @Test
    public void getNullableByteWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getByte(2)).thenReturn((byte) 7);

        assertEquals(7, ResultSets.getNullableByte(mockResultSet, 2).byteValue());
        assertEquals(7, ResultSets.enhance(mockResultSet).getNullableByte(2).byteValue());

        when(mockResultSet.getByte(2)).thenReturn((byte) 0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableByte(mockResultSet, 2));
        assertNull(ResultSets.enhance(mockResultSet).getNullableByte(2));
    }

    @Test
    public void getNullableByteWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getByte("foo")).thenReturn((byte) 7);

        assertEquals(7, ResultSets.getNullableByte(mockResultSet, "foo").byteValue());
        assertEquals(7, ResultSets.enhance(mockResultSet).getNullableByte("foo").byteValue());

        when(mockResultSet.getByte("foo")).thenReturn((byte) 0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getNullableByte(mockResultSet, "foo"));
        assertNull(ResultSets.enhance(mockResultSet).getNullableByte("foo"));
    }

    @Test
    public void getEnumFromNameWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString(2)).thenReturn("FOO");

        assertEquals(TestEnum.FOO, ResultSets.getEnumFromName(mockResultSet, 2, TestEnum.class));
        assertEquals(TestEnum.FOO, ResultSets.enhance(mockResultSet).getEnumFromName(2, TestEnum.class));

        when(mockResultSet.getString(2)).thenReturn(null);
        assertNull(ResultSets.getEnumFromName(mockResultSet, 2, TestEnum.class));
        assertNull(ResultSets.enhance(mockResultSet).getEnumFromName(2, TestEnum.class));
    }

    @Test
    public void getEnumFromNameWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("enum_column")).thenReturn("FOO");

        assertEquals(TestEnum.FOO, ResultSets.getEnumFromName(mockResultSet, "enum_column", TestEnum.class));
        assertEquals(TestEnum.FOO, ResultSets.enhance(mockResultSet).getEnumFromName("enum_column", TestEnum.class));

        when(mockResultSet.getString("enum_column")).thenReturn(null);
        assertNull(ResultSets.getEnumFromName(mockResultSet, "enum_column", TestEnum.class));
        assertNull(ResultSets.enhance(mockResultSet).getEnumFromName("enum_column", TestEnum.class));
    }

    @Test(expected = IllegalStateException.class)
    public void getEnumFromNameWithColumnIndexThrowsIllegalStateException() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString(2)).thenReturn("HELLO");
        ResultSets.getEnumFromName(mockResultSet, 2, TestEnum.class);
    }

    @Test(expected = IllegalStateException.class)
    public void getEnumFromNameWithColumnNameThrowsIllegalStateException() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("enum_column")).thenReturn("HELLO");
        ResultSets.getEnumFromName(mockResultSet, "enum_column", TestEnum.class);
    }

    @Test(expected = IllegalStateException.class)
    public void getEnumFromNameWithColumnIndexThrowsIllegalStateException2() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString(2)).thenReturn("HELLO");
        ResultSets.enhance(mockResultSet).getEnumFromName(2, TestEnum.class);
    }

    @Test(expected = IllegalStateException.class)
    public void getEnumFromNameWithColumnNameThrowsIllegalStateException2() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("enum_column")).thenReturn("HELLO");
        ResultSets.enhance(mockResultSet).getEnumFromName("enum_column", TestEnum.class);
    }

    @Test
    public void getEnumFromOrdinalWithColumnIndexWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt(2)).thenReturn(0);

        assertEquals(TestEnum.FOO, ResultSets.getEnumFromOrdinal(mockResultSet, 2, TestEnum.class));
        assertEquals(TestEnum.FOO, ResultSets.enhance(mockResultSet).getEnumFromOrdinal(2, TestEnum.class));

        when(mockResultSet.getInt(2)).thenReturn(0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getEnumFromOrdinal(mockResultSet, 2, TestEnum.class));
        assertNull(ResultSets.enhance(mockResultSet).getEnumFromOrdinal(2, TestEnum.class));
    }

    @Test
    public void getEnumFromOrdinalWithColumnNameWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("enum_column")).thenReturn(0);

        assertEquals(TestEnum.FOO, ResultSets.getEnumFromOrdinal(mockResultSet, "enum_column", TestEnum.class));
        assertEquals(TestEnum.FOO, ResultSets.enhance(mockResultSet).getEnumFromOrdinal("enum_column", TestEnum.class));

        when(mockResultSet.getInt("enum_column")).thenReturn(0);
        when(mockResultSet.wasNull()).thenReturn(true);
        assertNull(ResultSets.getEnumFromOrdinal(mockResultSet, "enum_column", TestEnum.class));
        assertNull(ResultSets.enhance(mockResultSet).getEnumFromOrdinal("enum_column", TestEnum.class));
    }

    @Test(expected = IllegalStateException.class)
    public void getEnumFromOrdinalWithColumnIndexThrowsIllegalStateException() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt(2)).thenReturn(2);
        ResultSets.getEnumFromOrdinal(mockResultSet, 2, TestEnum.class);
    }

    @Test(expected = IllegalStateException.class)
    public void getEnumFromOrdinalWithColumnNameThrowsIllegalStateException() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("enum_column")).thenReturn(2);
        ResultSets.getEnumFromOrdinal(mockResultSet, "enum_column", TestEnum.class);
    }

    @Test(expected = IllegalStateException.class)
    public void getEnumFromOrdinalWithColumnIndexThrowsIllegalStateException2() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt(2)).thenReturn(2);
        ResultSets.enhance(mockResultSet).getEnumFromOrdinal(2, TestEnum.class);
    }

    @Test(expected = IllegalStateException.class)
    public void getEnumFromOrdinalWithColumnNameThrowsIllegalStateException2() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("enum_column")).thenReturn(2);
        ResultSets.enhance(mockResultSet).getEnumFromOrdinal("enum_column", TestEnum.class);
    }

    @Test
    public void methodFromResultSetWorks() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("foo")).thenReturn(2);

        assertEquals(2, ResultSets.enhance(mockResultSet).getInt("foo"));
    }

    @Test
    public void equalsReturnsTrueOnSameEnhancedStatement() {
        ResultSet mockResultSet = mock(ResultSet.class);
        EnhancedResultSet enhanced = ResultSets.enhance(mockResultSet);
        assertTrue(enhanced.equals(enhanced));
    }
}
