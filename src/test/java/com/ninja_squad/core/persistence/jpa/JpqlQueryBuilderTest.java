package com.ninja_squad.core.persistence.jpa;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;

public class JpqlQueryBuilderTest {
    private JpqlQueryBuilder builder;
    private Date startDate = new Date(12345L);
    private Calendar endDate = Calendar.getInstance();

    @Before
    public void prepare() {
        builder = JpqlQueryBuilder.start();
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
               .setParameter("startDate", startDate, TemporalType.DATE)
               .setParameter("endDate", endDate, TemporalType.TIMESTAMP)
               .groupBy("cat.name")
               .groupBy("cat.color")
               .having("sum(cat.age) > 0")
               .orderBy("cat.name")
               .orderBy("cat.color");
    }

    @Test
    public void jpqlIsGeneratedCorrectly() {
        String jpql = builder.createJpql();
        String expected = "select cat.name, cat.color, sum(cat.age)"
                          + " from Cat cat"
                          + " inner join cat.owner owner"
                          + " where owner.lastName = :lastName"
                          + " and (cat.sex = 'male' or cat.sex = 'female')"
                          + " and (owner.birthDate >= :startDate and owner.birthDate <= :endDate)"
                          + " group by cat.name, cat.color"
                          + " having sum(cat.age) > 0"
                          + " order by cat.name, cat.color";

        assertEquals(expected, jpql);
    }

    @Test
    public void subQueryJpqlIsGeneratedCorrectly() {
        JpqlSubQueryBuilder sb = builder.subQueryBuilder();
        sb.select("toy.id")
          .from("Toy toy")
          .where("toy.kind = 'mouse'");
        String expected = "select toy.id"
                          + " from Toy toy"
                          + " where toy.kind = 'mouse'";

        assertEquals(expected, sb.toJpql());
        assertEquals(" (" + expected + ") ", sb.toWrappedJpql());
    }

    @Test
    public void parametersAreBoundCorrectly() {
        Query mockQuery = mock(Query.class);
        EntityManager mockEm = mock(EntityManager.class);
        when(mockEm.createQuery(builder.createJpql())).thenReturn(mockQuery);

        Query result = builder.createQuery(mockEm);

        verify(mockQuery).setParameter("lastName", "Smith");
        verify(mockQuery).setParameter("startDate", startDate, TemporalType.DATE);
        verify(mockQuery).setParameter("endDate", endDate, TemporalType.TIMESTAMP);

        assertSame(mockQuery, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void parametersAreBoundCorrectlyWithTypedQuery() {
        TypedQuery<Object[]> mockQuery = mock(TypedQuery.class);
        EntityManager mockEm = mock(EntityManager.class);
        when(mockEm.createQuery(builder.createJpql(), Object[].class)).thenReturn(mockQuery);

        TypedQuery<Object[]> result = builder.createQuery(mockEm, Object[].class);

        verify(mockQuery).setParameter("lastName", "Smith");
        verify(mockQuery).setParameter("startDate", startDate, TemporalType.DATE);
        verify(mockQuery).setParameter("endDate", endDate, TemporalType.TIMESTAMP);

        assertSame(mockQuery, result);
    }

    @Test
    public void startDistinctWorks() {
        JpqlQueryBuilder qb = JpqlQueryBuilder.startDistinct().select("foo").from("Foo foo");
        String jpql = qb.createJpql();
        assertEquals("select distinct foo from Foo foo", jpql);
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
        JpqlQueryBuilder qb = JpqlQueryBuilder.startDistinct().select("foo").from("Foo foo");
        JpqlSubQueryBuilder sb = qb.subQueryBuilder();
        sb.select("bar.id")
          .from("Bar bar")
          .where("bar.id = :barId")
          .setParameter("barId", 1)
          .where("bar.lastName = :lastName")
          .where("bar.startDate >= :startDate")
          .where("bar.endDate <= :endDate")
          .setParameter("lastName", "Smith")
          .setParameter("startDate", startDate, TemporalType.DATE)
          .setParameter("endDate", endDate, TemporalType.TIMESTAMP);

        qb.where("foo.id in" + sb.toWrappedJpql());

        Query mockQuery = mock(Query.class);
        EntityManager mockEm = mock(EntityManager.class);
        when(mockEm.createQuery(qb.createJpql())).thenReturn(mockQuery);

        Query result = sb.createQuery(mockEm);

        verify(mockQuery).setParameter("lastName", "Smith");
        verify(mockQuery).setParameter("startDate", startDate, TemporalType.DATE);
        verify(mockQuery).setParameter("endDate", endDate, TemporalType.TIMESTAMP);

        assertSame(mockQuery, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createDistinctSubQueryBuilderWorks() {
        JpqlQueryBuilder qb = JpqlQueryBuilder.startDistinct().select("foo").from("Foo foo");
        JpqlSubQueryBuilder sb = qb.distinctSubQueryBuilder();
        sb.select("bar.id")
          .from("Bar bar")
          .where("bar.id = :barId")
          .setParameter("barId", 1)
          .where("bar.lastName = :lastName")
          .where("bar.startDate >= :startDate")
          .where("bar.endDate <= :endDate")
          .setParameter("lastName", "Smith")
          .setParameter("startDate", startDate, TemporalType.DATE)
          .setParameter("endDate", endDate, TemporalType.TIMESTAMP);

        qb.where("foo.id in" + sb.toWrappedJpql());

        TypedQuery<Object[]> mockQuery = mock(TypedQuery.class);
        EntityManager mockEm = mock(EntityManager.class);
        when(mockEm.createQuery(qb.createJpql(), Object[].class)).thenReturn(mockQuery);

        Query result = sb.createQuery(mockEm, Object[].class);

        verify(mockQuery).setParameter("lastName", "Smith");
        verify(mockQuery).setParameter("startDate", startDate, TemporalType.DATE);
        verify(mockQuery).setParameter("endDate", endDate, TemporalType.TIMESTAMP);

        assertSame(mockQuery, result);
    }
}
