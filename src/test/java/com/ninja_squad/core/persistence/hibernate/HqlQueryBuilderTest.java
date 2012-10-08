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

package com.ninja_squad.core.persistence.hibernate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class HqlQueryBuilderTest {
    private HqlQueryBuilder builder;
    private Date startDate = new Date(12345L);
    private Date endDate = new Date(123456L);
    private Date creationDate = new Date(1234567L);

    @Before
    public void prepare() {
        builder = HqlQueryBuilder.start();
        builder.select("cat.name")
               .select("cat.color")
               .select("sum(cat.age)")
               .from("Cat cat")
               .from("inner join cat.owner owner")
               .where("owner.lastName = :lastName")
               .setParameter("lastName", "Smith")
               .where(builder.disjunction("cat.sex = 'male'",
                                          "cat.sex = 'female'"))
               .where(builder.conjunction(Arrays.asList("owner.birthDate >= :startDate",
                                                        "owner.birthDate <= :endDate")))
               .where("cat.creationTime < :creationDate")
               .setDateParameter("startDate", startDate)
               .setTimestampParameter("endDate", endDate)
               .setTimeParameter("creationDate", creationDate)
               .groupBy("cat.name")
               .groupBy("cat.color")
               .having("sum(cat.age) > 0")
               .orderBy("cat.name")
               .orderBy("cat.color");
    }

    @Test
    public void hqlIsGeneratedCorrectly() {
        String hql = builder.createHql();
        String expected = "select cat.name, cat.color, sum(cat.age)"
                          + " from Cat cat"
                          + " inner join cat.owner owner"
                          + " where owner.lastName = :lastName"
                          + " and (cat.sex = 'male' or cat.sex = 'female')"
                          + " and (owner.birthDate >= :startDate and owner.birthDate <= :endDate)"
                          + " and cat.creationTime < :creationDate"
                          + " group by cat.name, cat.color"
                          + " having sum(cat.age) > 0"
                          + " order by cat.name, cat.color";

        assertEquals(expected, hql);
    }

    @Test
    public void subQueryHqlIsGeneratedCorrectly() {
        HqlSubQueryBuilder sb = builder.subQueryBuilder();
        sb.select("toy.id")
          .from("Toy toy")
          .where("toy.kind = 'mouse'");
        String expected = "select toy.id"
                          + " from Toy toy"
                          + " where toy.kind = 'mouse'";

        assertEquals(expected, sb.toHql());
        assertEquals(" (" + expected + ") ", sb.toWrappedHql());
    }

    @Test
    public void parametersAreBoundCorrectly() {
        Query mockQuery = mock(Query.class);
        Session mockSession = mock(Session.class);
        when(mockSession.createQuery(builder.createHql())).thenReturn(mockQuery);

        Query result = builder.createQuery(mockSession);

        verify(mockQuery).setParameter("lastName", "Smith");
        verify(mockQuery).setDate("startDate", startDate);
        verify(mockQuery).setTimestamp("endDate", endDate);
        verify(mockQuery).setTime("creationDate", creationDate);

        assertSame(mockQuery, result);
    }

    @Test
    public void startDistinctWorks() {
        HqlQueryBuilder qb = HqlQueryBuilder.startDistinct().select("foo").from("Foo foo");
        String hql = qb.createHql();
        assertEquals("select distinct foo from Foo foo", hql);
    }

    @Test
    public void disjunctionWithCollectionWorks() {
        assertEquals("(A or B or C)", builder.disjunction(Arrays.asList("A", "B", "C")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void disjunctionThrowsExceptionIfEmptyCollection() {
        builder.disjunction(Collections.<String>emptyList());
    }

    @Test
    public void disjunctionWithVarargsWorks() {
        assertEquals("(A or B or C or D)", builder.disjunction("A", "B", "C", "D"));
    }

    @Test
    public void conjunctionWithCollectionWorks() {
        assertEquals("(A and B and C)", builder.conjunction(Arrays.asList("A", "B", "C")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void conjunctionThrowsExceptionIfEmptyCollection() {
        builder.conjunction(Collections.<String>emptyList());
    }

    @Test
    public void conjunctionWithVarargsWorks() {
        assertEquals("(A and B and C and D)", builder.conjunction("A", "B", "C", "D"));
    }

    @Test
    public void subQueryBuilderWorks() {
        HqlQueryBuilder qb = HqlQueryBuilder.startDistinct().select("foo").from("Foo foo");
        HqlSubQueryBuilder sb = qb.subQueryBuilder();
        sb.select("bar.id")
          .from("Bar bar")
          .where("bar.id = :barId")
          .setParameter("barId", 1)
          .where("bar.lastName = :lastName")
          .where("bar.startDate >= :startDate")
          .where("bar.endDate <= :endDate")
          .where("bar.creationDate < :creationDate")
          .setParameter("lastName", "Smith")
          .setDateParameter("startDate", startDate)
          .setTimestampParameter("endDate", endDate)
          .setTimeParameter("creationDate", creationDate);

        qb.where("foo.id in (" + sb.toHql() + ")");

        Query mockQuery = mock(Query.class);
        Session mockSession = mock(Session.class);
        when(mockSession.createQuery(qb.createHql())).thenReturn(mockQuery);

        Query result = sb.createQuery(mockSession);

        verify(mockQuery).setParameter("lastName", "Smith");
        verify(mockQuery).setDate("startDate", startDate);
        verify(mockQuery).setTimestamp("endDate", endDate);
        verify(mockQuery).setTime("creationDate", creationDate);

        assertSame(mockQuery, result);
    }

    @Test
    public void createDistinctSubQueryBuilderWorks() {
        HqlQueryBuilder qb = HqlQueryBuilder.startDistinct().select("foo").from("Foo foo");
        HqlSubQueryBuilder sb = qb.distinctSubQueryBuilder();
        sb.select("bar.id")
          .from("Bar bar")
          .where("bar.id = :barId")
          .setParameter("barId", 1)
          .where("bar.lastName = :lastName")
          .where("bar.startDate >= :startDate")
          .where("bar.endDate <= :endDate")
          .where("bar.creationDate < :creationDate")
          .setParameter("lastName", "Smith")
          .setDateParameter("startDate", startDate)
          .setTimestampParameter("endDate", endDate)
          .setTimeParameter("creationDate", creationDate);

        qb.where("foo.id in (" + sb.toHql() + ")");

        Query mockQuery = mock(Query.class);
        Session mockSession = mock(Session.class);
        when(mockSession.createQuery(qb.createHql())).thenReturn(mockQuery);

        Query result = sb.createQuery(mockSession);

        verify(mockQuery).setParameter("lastName", "Smith");
        verify(mockQuery).setDate("startDate", startDate);
        verify(mockQuery).setTimestamp("endDate", endDate);
        verify(mockQuery).setTime("creationDate", creationDate);

        assertSame(mockQuery, result);
    }
}
