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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PreparedStatementsTest {
    private enum TestEnum {
        FOO, BAR;
    }

    @Test
    public void enhanceShouldNotEnhanceTwice() {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        EnhancedPreparedStatement enhanced1 = PreparedStatements.enhance(mockPreparedStatement);
        EnhancedPreparedStatement enhanced2 = PreparedStatements.enhance(enhanced1);
        assertSame(enhanced1, enhanced2);
    }

    @Test
    public void setNullableIntWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableInt(mockPreparedStatement, 1, 18);
        enhanced.setNullableInt(2, 19);

        verify(mockPreparedStatement).setInt(1, 18);
        verify(mockPreparedStatement).setInt(2, 19);
    }

    @Test
    public void setNullableIntWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableInt(mockPreparedStatement, 1, null);
        enhanced.setNullableInt(2, null);

        verify(mockPreparedStatement).setNull(1, Types.INTEGER);
        verify(mockPreparedStatement).setNull(2, Types.INTEGER);
    }

    @Test
    public void setNullableLongWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableLong(mockPreparedStatement, 1, 18L);
        enhanced.setNullableLong(2, 19L);

        verify(mockPreparedStatement).setLong(1, 18L);
        verify(mockPreparedStatement).setLong(2, 19L);
    }

    @Test
    public void setNullableLongWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableLong(mockPreparedStatement, 1, null);
        enhanced.setNullableLong(2, null);

        verify(mockPreparedStatement).setNull(1, Types.BIGINT);
        verify(mockPreparedStatement).setNull(2, Types.BIGINT);
    }

    @Test
    public void setNullableBooleanWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableBoolean(mockPreparedStatement, 1, true);
        enhanced.setNullableBoolean(2, true);

        verify(mockPreparedStatement).setBoolean(1, true);
        verify(mockPreparedStatement).setBoolean(2, true);
    }

    @Test
    public void setNullableBooleanWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableBoolean(mockPreparedStatement, 1, null);
        enhanced.setNullableBoolean(2, null);

        verify(mockPreparedStatement).setNull(1, Types.BOOLEAN);
        verify(mockPreparedStatement).setNull(2, Types.BOOLEAN);
    }

    @Test
    public void setNullableDoubleWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableDouble(mockPreparedStatement, 1, 18.0);
        enhanced.setNullableDouble(2, 19.0);

        verify(mockPreparedStatement).setDouble(1, 18.0);
        verify(mockPreparedStatement).setDouble(2, 19.0);
    }

    @Test
    public void setNullableDoubleWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableDouble(mockPreparedStatement, 1, null);
        enhanced.setNullableDouble(2, null);

        verify(mockPreparedStatement).setNull(1, Types.DOUBLE);
        verify(mockPreparedStatement).setNull(2, Types.DOUBLE);
    }

    @Test
    public void setNullableFloatWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableFloat(mockPreparedStatement, 1, 18.0F);
        enhanced.setNullableFloat(2, 19.0F);

        verify(mockPreparedStatement).setFloat(1, 18.0F);
        verify(mockPreparedStatement).setFloat(2, 19.0F);
    }

    @Test
    public void setNullableFloatWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableFloat(mockPreparedStatement, 1, null);
        enhanced.setNullableFloat(2, null);

        verify(mockPreparedStatement).setNull(1, Types.REAL);
        verify(mockPreparedStatement).setNull(2, Types.REAL);
    }

    @Test
    public void setNullableByteWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableByte(mockPreparedStatement, 1, (byte) 18);
        enhanced.setNullableByte(2, (byte) 19);

        verify(mockPreparedStatement).setByte(1, (byte) 18);
        verify(mockPreparedStatement).setByte(2, (byte) 19);
    }

    @Test
    public void setNullableByteWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableByte(mockPreparedStatement, 1, null);
        enhanced.setNullableByte(2, null);

        verify(mockPreparedStatement).setNull(1, Types.TINYINT);
        verify(mockPreparedStatement).setNull(2, Types.TINYINT);
    }

    @Test
    public void setNullableShortWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableShort(mockPreparedStatement, 1, (short) 18);
        enhanced.setNullableShort(2, (short) 19);

        verify(mockPreparedStatement).setShort(1, (short) 18);
        verify(mockPreparedStatement).setShort(2, (short) 19);
    }

    @Test
    public void setNullableShortWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setNullableShort(mockPreparedStatement, 1, null);
        enhanced.setNullableShort(2, null);

        verify(mockPreparedStatement).setNull(1, Types.SMALLINT);
        verify(mockPreparedStatement).setNull(2, Types.SMALLINT);
    }

    @Test
    public void setEnumAsNameWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setEnumAsName(mockPreparedStatement, 1, TestEnum.FOO);
        enhanced.setEnumAsName(2, TestEnum.BAR);

        verify(mockPreparedStatement).setString(1, "FOO");
        verify(mockPreparedStatement).setString(2, "BAR");
    }

    @Test
    public void setEnumAsNameWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setEnumAsName(mockPreparedStatement, 1, null);
        enhanced.setEnumAsName(2, null);

        verify(mockPreparedStatement).setString(1, null);
        verify(mockPreparedStatement).setString(2, null);
    }

    @Test
    public void setEnumAsOrdinalWorksWithNonNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setEnumAsOrdinal(mockPreparedStatement, 1, TestEnum.FOO);
        enhanced.setEnumAsOrdinal(2, TestEnum.BAR);

        verify(mockPreparedStatement).setInt(1, 0);
        verify(mockPreparedStatement).setInt(2, 1);
    }

    @Test
    public void setEnumAsOrdinalWorksWithNullValue() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        PreparedStatements.setEnumAsOrdinal(mockPreparedStatement, 1, null);
        enhanced.setEnumAsOrdinal(2, null);

        verify(mockPreparedStatement).setNull(1, Types.INTEGER);
        verify(mockPreparedStatement).setNull(2, Types.INTEGER);
    }

    @Test
    public void methodFromPreparedStatementWorks() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        PreparedStatements.enhance(mockPreparedStatement).setString(1, "foo");
        verify(mockPreparedStatement).setString(1, "foo");
    }

    @Test
    public void equalsReturnsTrueOnSameEnhancedStatement() {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        EnhancedPreparedStatement enhanced = PreparedStatements.enhance(mockPreparedStatement);
        assertTrue(enhanced.equals(enhanced));
    }
}
