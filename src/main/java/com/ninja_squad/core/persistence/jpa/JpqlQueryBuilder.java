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

package com.ninja_squad.core.persistence.jpa;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.google.common.base.Preconditions;
import com.ninja_squad.core.persistence.BaseQueryBuilder;

/**
 * Allows creating a JPQL query dynamically. This class has the following drawbacks compared to
 * the Criteria API:
 * <ul>
 *   <li>it's not type-safe</li>
 *   <li>it doesn't make any validation regarding the correctness of the query (just as a static JPQL query)</li>
 * </ul>
 * It's useful though, because
 * <ul>
 *   <li>it's easier to use, read and maintain than the criteria API</li>
 *   <li>it has the full power of JPQL queries, and is not limited as the Criteria API can be for some queries</li>
 * </ul>
 *
 * Example usage:
 * <pre>
 *   JpqlQueryBuilder qb = JpqlQueryBuilder.start();
 *   qb.select("cat.id")
 *     .select("cat.name")
 *     .from("Cat cat")
 *     .from("inner join cat.owner owner")
 *     .where("owner.lastName = :lastName")
 *     .setParameter("lastName", "Smith");
 *   if (firstName != null) {
 *       qb.where("owner.firstName = :firstName")
 *         .setParameter("firstName", firstName);
 *   }
 *   qb.orderBy("cat.name desc");
 *   Query query = qb.createQuery(entityManager);
 * </pre>
 *
 * @author JB Nizet
 */
public class JpqlQueryBuilder extends BaseQueryBuilder<Query> {

    /**
     * Constructor
     * @param distinct indicates if the query is a <code>select...</code> or a
     * <code>select distinct...</code>
     */
    JpqlQueryBuilder(boolean distinct) {
        super(distinct);
    }

    /**
     * Creates a new instance of this class, for a <code>select...</code> query.
     * @return the created builder
     */
    public static JpqlQueryBuilder start() {
        return new JpqlQueryBuilder(false);
    }

    /**
     * Creates a new instance of this class, for a <code>select distinct...</code> query.
     * @return the created builder
     */
    public static JpqlQueryBuilder startDistinct() {
        return new JpqlQueryBuilder(true);
    }

    /**
     * Adds a clause element to the <em>select</em> clause. The elements are automatically separated by commas.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder select(@Nonnull String clause) {
        addSelect(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>from</em> clause. The elements are automatically separated by spaces.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder from(@Nonnull String clause) {
        addFrom(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>where</em> clause. The elements are automatically separated by <code>and</code>.
     * To add elements separated by <code>or</code>, use a disjunction.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder where(@Nonnull String clause) {
        addWhere(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>group by</em> clause. The elements are automatically separated by commas.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder groupBy(@Nonnull String clause) {
        addGroupBy(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>having</em> clause. The elements are automatically
     * separated by <code>and</code>.
     * To add elements separated by <code>or</code>, use a disjunction.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder having(@Nonnull String clause) {
        addHaving(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>order by</em> clause. The elements are automatically
     * separated by commas.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder orderBy(@Nonnull String clause) {
        addOrderBy(clause);
        return this;
    }

    /**
     * Sets a parameter that will be bound when the query will be created. Note that the same
     * parameter may be set several times. The last set value wins.
     * @param name the name of the parameter (without the <code>:</code> prefix)
     * @param value the value of the parameter
     * @return this
     */
    public JpqlQueryBuilder setParameter(@Nonnull String name, Object value) {
        setParameterBinder(name, new SimpleParameterBinder(name, value));
        return this;
    }

    /**
     * Sets a temporal parameter that will be bound when the query will be created. Note that
     * the same parameter may be set several times. The last set value wins.
     * @param name the name of the parameter (without the <code>:</code> prefix)
     * @param value the value of the parameter
     * @param temporalType the type of the temporal parameter
     * @return this
     */
    public JpqlQueryBuilder setParameter(@Nonnull String name, Date value, TemporalType temporalType) {
        setParameterBinder(name, new DateParameterBinder(name, value, temporalType));
        return this;
    }

    /**
     * Sets a temporal parameter that will be bound when the query will be created. Note that
     * the same parameter may be set several times. The last set value wins.
     * @param name the name of the parameter (without the <code>:</code> prefix)
     * @param value the value of the parameter
     * @param temporalType the type of the temporal parameter
     * @return this
     */
    public JpqlQueryBuilder setParameter(@Nonnull String name, Calendar value, TemporalType temporalType) {
        setParameterBinder(name, new CalendarParameterBinder(name, value, temporalType));
        return this;
    }

    /**
     * Creates a typed query, and binds the parameters that have been set to this builder.
     * @param entityManager the entity manager used to create the query
     * @param resultType the type of the result of the query
     * @return the typed query created
     */
    public <T> TypedQuery<T> createQuery(@Nonnull EntityManager entityManager, @Nonnull Class<T> resultType) {
        Preconditions.checkNotNull(entityManager, "entityManager may not be null");
        Preconditions.checkNotNull(resultType, "resultType may not be null");
        TypedQuery<T> query = entityManager.createQuery(createJpql(), resultType);
        bindParameters(query);
        return query;
    }

    /**
     * Creates a query, and binds the parameters that have been set to this builder.
     * @param entityManager the entity manager used to create the query
     * @return the query created
     */
    public Query createQuery(@Nonnull EntityManager entityManager) {
        Preconditions.checkNotNull(entityManager, "entityManager may not be null");
        Query query = entityManager.createQuery(createJpql());
        bindParameters(query);
        return query;
    }

    /**
     * Allows creating a subquery that can be added as a from, where or having clause. For example:
     * <pre>
     *   JpqlSubqueryBuilder sb = qb.subQueryBuilder();
     *   sb.select("bar.id")
     *     .from("Bar bar")
     *     .where("bar.name = :barName")
     *     .setParameter("barName", barName);
     *   qb.where("foo.id in " + sb.toWrappedJpql())");
     * </pre>
     *
     * The subquery does a <code>select...</code>
     */
    public JpqlSubQueryBuilder subQueryBuilder() {
        return new JpqlSubQueryBuilder(this, false);
    }

    /**
     * Allows creating a subquery that can be added as a from, where or having clause.
     * The subquery does a <code>select distinct...</code>. Otherwise, its usage is similar to the one
     * of {@link #subQueryBuilder()}.
     */
    public JpqlSubQueryBuilder distinctSubQueryBuilder() {
        return new JpqlSubQueryBuilder(this, true);
    }

    final String createJpql() {
        return createQueryString();
    }

    /**
     * ParameterBinder implementation for simple (i.e. not temporal) parameters
     * @author JB Nizet
     */
    private static final class SimpleParameterBinder implements ParameterBinder<Query> {
        private final String name;
        private final Object value;

        public SimpleParameterBinder(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public void bind(Query query) {
            query.setParameter(name, value);
        }
    }

    /**
     * ParameterBinder implementation for date parameters
     * @author JB Nizet
     */
    private static final class DateParameterBinder implements ParameterBinder<Query> {
        private final String name;
        private final Date value;
        private final TemporalType temporalType;

        public DateParameterBinder(String name, Date value, TemporalType temporalType) {
            this.name = name;
            this.value = (Date) value.clone();
            this.temporalType = temporalType;
        }

        @Override
        public void bind(Query query) {
            query.setParameter(name, value, temporalType);
        }
    }

    /**
     * ParameterBinder implementation for calendar parameters
     * @author JB Nizet
     */
    private static final class CalendarParameterBinder implements ParameterBinder<Query> {
        private final String name;
        private final Calendar value;
        private final TemporalType temporalType;

        public CalendarParameterBinder(String name, Calendar value, TemporalType temporalType) {
            this.name = name;
            this.value = (Calendar) value.clone();
            this.temporalType = temporalType;
        }

        @Override
        public void bind(Query query) {
            query.setParameter(name, value, temporalType);
        }
    }
}
