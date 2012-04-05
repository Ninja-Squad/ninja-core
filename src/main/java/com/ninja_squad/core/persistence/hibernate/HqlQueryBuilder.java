package com.ninja_squad.core.persistence.hibernate;

import java.util.Date;

import javax.annotation.Nonnull;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.common.base.Preconditions;
import com.ninja_squad.core.persistence.BaseQueryBuilder;
import com.ninja_squad.core.persistence.jpa.JpqlQueryBuilder;

/**
 * Allows creating a HQL query dynamically. This class has the following drawbacks compared to
 * the Criteria API:
 * <ul>
 *   <li>it's not type-safe</li>
 *   <li>it doesn't make any validation regarding the correctness of the query (just as a static HQL query)</li>
 * </ul>
 * It's useful though, because
 * <ul>
 *   <li>it's easier to use, read and maintain than the criteria API</li>
 *   <li>it has the full power of HQL queries, and is not limited as the Criteria API can be for some queries</li>
 * </ul>
 *
 * Example usage:
 * <pre>
 *   HqlQueryBuilder qb = HqlQueryBuilder.start();
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
 *   Query query = qb.createQuery(session);
 * </pre>
 *
 * Note that, to be consistent with {@link JpqlQueryBuilder} and because we
 * think it's better to always have one, the select clause is mandatory.
 *
 * @author JB
 */
public class HqlQueryBuilder extends BaseQueryBuilder<Query> {

    /**
     * Constructor
     * @param distinct indicates if the query is a <code>select...</code> or a
     * <code>select distinct...</code>
     */
    HqlQueryBuilder(boolean distinct) {
        super(distinct);
    }

    /**
     * Creates a new instance of this class, for a <code>select...</code> query.
     * @return the created builder
     */
    public static HqlQueryBuilder start() {
        return new HqlQueryBuilder(false);
    }

    /**
     * Creates a new instance of this class, for a <code>select distinct...</code> query.
     * @return the created builder
     */
    public static HqlQueryBuilder startDistinct() {
        return new HqlQueryBuilder(true);
    }

    /**
     * Adds a clause element to the <em>select</em> clause. The elements are automatically separated by commas.
     * @param clause the clause element to add.
     * @return this
     */
    public HqlQueryBuilder select(@Nonnull String clause) {
        addSelect(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>from</em> clause. The elements are automatically separated by spaces.
     * @param clause the clause element to add.
     * @return this
     */
    public HqlQueryBuilder from(@Nonnull String clause) {
        addFrom(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>where</em> clause. The elements are automatically separated by <code>and</code>.
     * To add elements separated by <code>or</code>, use a disjunction.
     * @param clause the clause element to add.
     * @return this
     */
    public HqlQueryBuilder where(@Nonnull String clause) {
        addWhere(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>group by</em> clause. The elements are automatically separated by commas.
     * @param clause the clause element to add.
     * @return this
     */
    public HqlQueryBuilder groupBy(@Nonnull String clause) {
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
    public HqlQueryBuilder having(@Nonnull String clause) {
        addHaving(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>order by</em> clause. The elements are automatically
     * separated by commas.
     * @param clause the clause element to add.
     * @return this
     */
    public HqlQueryBuilder orderBy(@Nonnull String clause) {
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
    public HqlQueryBuilder setParameter(@Nonnull String name, Object value) {
        setParameterBinder(name, new SimpleParameterBinder(name, value));
        return this;
    }

    /**
     * Sets a parameter of type Date (as opposed to Timestamp and Time) that will be bound when the query will be
     * created. Note that the same parameter may be set several times. The last set value wins.
     * @param name the name of the parameter (without the <code>:</code> prefix)
     * @param value the value of the parameter
     * @return this
     */
    public HqlQueryBuilder setDateParameter(@Nonnull String name, Date value) {
        setParameterBinder(name, new DateParameterBinder(name, value));
        return this;
    }

    /**
     * Sets a parameter of type Timestamp (as opposed to Date and Time) that will be bound when the query will be
     * created. Note that the same parameter may be set several times. The last set value wins.
     * @param name the name of the parameter (without the <code>:</code> prefix)
     * @param value the value of the parameter
     * @return this
     */
    public HqlQueryBuilder setTimestampParameter(@Nonnull String name, Date value) {
        setParameterBinder(name, new TimestampParameterBinder(name, value));
        return this;
    }

    /**
     * Sets a parameter of type Time (as opposed to Date and Timestamp) that will be bound when the query will be
     * created. Note that the same parameter may be set several times. The last set value wins.
     * @param name the name of the parameter (without the <code>:</code> prefix)
     * @param value the value of the parameter
     * @return this
     */
    public HqlQueryBuilder setTimeParameter(@Nonnull String name, Date value) {
        setParameterBinder(name, new TimeParameterBinder(name, value));
        return this;
    }

    /**
     * Creates a query, and binds the parameters that have been set to this builder.
     * @param session the session used to create the query
     * @return the query created
     */
    public Query createQuery(@Nonnull Session session) {
        Preconditions.checkNotNull(session, "session may not be null");
        Query query = session.createQuery(createHql());
        bindParameters(query);
        return query;
    }

    /**
     * Allows creating a subquery that can be added as a from, where or having clause. For example:
     * <pre>
     *   qb.where("foo.name in ("
     *            + qb.subQueryBuilder().select("bar.id")
     *                                  .from("Bar bar")
     *                                  .where("bar.name = :barName")
     *                                  .setParameter("barName", barName)
     *                                  .toHql()
     *            + ")");
     * </pre>
     *
     * The subquery does a <code>select...</code>
     */
    public HqlSubQueryBuilder subQueryBuilder() {
        return new HqlSubQueryBuilder(this, false);
    }

    /**
     * Allows creating a subquery that can be added as a from, where or having clause.
     * The subquery does a <code>select distinct...</code>. Otherwise, its usage is similar to the one
     * of {@link #subQueryBuilder()}.
     */
    public HqlSubQueryBuilder distinctSubQueryBuilder() {
        return new HqlSubQueryBuilder(this, true);
    }

    final String createHql() {
        return createQueryString();
    }

    /**
     * ParameterBinder implementation for simple (i.e. not temporal) parameters
     * @author JB
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
     * @author JB
     */
    private static final class DateParameterBinder implements ParameterBinder<Query> {
        private final String name;
        private final Date value;

        public DateParameterBinder(String name, Date value) {
            this.name = name;
            this.value = (Date) value.clone();
        }

        @Override
        public void bind(Query query) {
            query.setDate(name, value);
        }
    }

    /**
     * ParameterBinder implementation for timestamp parameters
     * @author JB
     */
    private static final class TimestampParameterBinder implements ParameterBinder<Query> {
        private final String name;
        private final Date value;

        public TimestampParameterBinder(String name, Date value) {
            this.name = name;
            this.value = (Date) value.clone();
        }

        @Override
        public void bind(Query query) {
            query.setTimestamp(name, value);
        }
    }

    /**
     * ParameterBinder implementation for date parameters
     * @author JB
     */
    private static final class TimeParameterBinder implements ParameterBinder<Query> {
        private final String name;
        private final Date value;

        public TimeParameterBinder(String name, Date value) {
            this.name = name;
            this.value = (Date) value.clone();
        }

        @Override
        public void bind(Query query) {
            query.setTime(name, value);
        }
    }
}
