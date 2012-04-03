package com.ninja_squad.core.persistence.jpa;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Allows creating a JPQL query dynamically. This class ahs the following drawbacks compared to
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
 * @author JB
 */
public class JpqlQueryBuilder {

    private boolean distinct;
    private List<String> selectClauses = Lists.newArrayList();
    private List<String> fromClauses = Lists.newArrayList();
    private List<String> whereClauses = Lists.newArrayList();
    private List<String> groupByClauses = Lists.newArrayList();
    private List<String> havingClauses = Lists.newArrayList();
    private List<String> orderByClauses = Lists.newArrayList();

    private Map<String, ParameterBinder> parameters = new HashMap<String, ParameterBinder>();

    /**
     * Constructor
     * @param distinct indicates if the query is a <code>select...</code> or a
     * <code>select distinct...</code>
     */
    JpqlQueryBuilder(boolean distinct) {
        this.distinct = distinct;
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
        Preconditions.checkNotNull(clause, "clause may not be null");
        selectClauses.add(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>from</em> clause. The elements are automatically separated by spaces.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder from(@Nonnull String clause) {
        Preconditions.checkNotNull(clause, "clause may not be null");
        fromClauses.add(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>where</em> clause. The elements are automatically separated by <code>and</code>.
     * To add elements separated by <code>or</code>, use a disjunction.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder where(@Nonnull String clause) {
        Preconditions.checkNotNull(clause, "clause may not be null");
        whereClauses.add(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>group by</em> clause. The elements are automatically separated by commas.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder groupBy(@Nonnull String clause) {
        Preconditions.checkNotNull(clause, "clause may not be null");
        groupByClauses.add(clause);
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
        Preconditions.checkNotNull(clause, "clause may not be null");
        havingClauses.add(clause);
        return this;
    }

    /**
     * Adds a clause element to the <em>order by</em> clause. The elements are automatically
     * separated by commas.
     * @param clause the clause element to add.
     * @return this
     */
    public JpqlQueryBuilder orderBy(@Nonnull String clause) {
        Preconditions.checkNotNull(clause, "clause may not be null");
        orderByClauses.add(clause);
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
        Preconditions.checkNotNull(name, "name may not be null");
        parameters.put(name, new SimpleParameterBinder(name, value));
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
        Preconditions.checkNotNull(name, "name may not be null");
        parameters.put(name, new DateParameterBinder(name, value, temporalType));
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
        Preconditions.checkNotNull(name, "name may not be null");
        parameters.put(name, new CalendarParameterBinder(name, value, temporalType));
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
     *   qb.where("foo.name in ("
     *            + qb.subQueryBuilder().select("bar.id")
     *                                  .from("Bar bar")
     *                                  .where("bar.name = :barName")
     *                                  .setParameter("barName", barName)
     *                                  .toJpql()
     *            + ")");
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

    /**
     * Creates a conjunction (i.e. <code>A and B and C</code>) from the individual given clauses
     * @param clauses the clauses to join in a conjunction (i.e. A, B, C)
     * @return the clauses, joined with <code>and</code> and enclosed in parentheses.
     * @throws IllegalArgumentException if the clauses collection is empty
     */
    public String conjunction(@Nonnull Collection<String> clauses) {
        Preconditions.checkNotNull(clauses, "clauses may not be null");
        Preconditions.checkArgument(!clauses.isEmpty(), "clauses may not be empty");
        return "(" + Joiner.on(" and ").join(clauses) + ")";
    }

    /**
     * Creates a conjunction (i.e. <code>A and B and C</code>) from the individual given clauses
     * @param firstClause the first clause to join in a conjunction
     * @param secondClause the first clause to join in a conjunction
     * @param otherClauses the other clauses to join in a conjunction
     * @return the clauses, joined with <code>and</code> and enclosed in parentheses.
     * @throws IllegalArgumentException if the clauses collection is empty
     */
    public String conjunction(@Nonnull String firstClause, @Nonnull String secondClause, String... otherClauses) {
        Preconditions.checkNotNull(firstClause, "firstClause may not be null");
        Preconditions.checkNotNull(secondClause, "secondClause may not be null");
        Preconditions.checkNotNull(otherClauses, "other clauses may not be null");
        for (String clause : otherClauses) {
            Preconditions.checkNotNull(clause, "other clauses may not be null");
        }

        return "(" + Joiner.on(" and ").join(firstClause, secondClause, (Object[]) otherClauses) + ")";
    }

    /**
     * Creates a disjunction (i.e. <code>A or B or C</code>) from the individual given clauses
     * @param clauses the clauses to join in a disjunction (i.e. A, B, C)
     * @return the clauses, joined with <code>or</code> and enclosed in parentheses.
     * @throws IllegalArgumentException if the clauses collection is empty
     */
    public String disjunction(@Nonnull Collection<String> clauses) {
        Preconditions.checkNotNull(clauses, "clauses may not be null");
        Preconditions.checkArgument(!clauses.isEmpty(), "clauses may not be empty");

        return "(" + Joiner.on(" or ").join(clauses) + ")";
    }

    /**
     * Creates a disjunction (i.e. <code>A or B or C</code>) from the individual given clauses
     * @param firstClause the first clause to join in a disjunction
     * @param secondClause the first clause to join in a disjunction
     * @param otherClauses the other clauses to join in a disjunction
     * @return the clauses, joined with <code>or</code> and enclosed in parentheses.
     * @throws IllegalArgumentException if the clauses collection is empty
     */
    public String disjunction(@Nonnull String firstClause, @Nonnull String secondClause, String... otherClauses) {
        Preconditions.checkNotNull(firstClause, "firstClause may not be null");
        Preconditions.checkNotNull(secondClause, "secondClause may not be null");
        Preconditions.checkNotNull(otherClauses, "other clauses may not be null");
        for (String clause : otherClauses) {
            Preconditions.checkNotNull(clause, "other clauses may not be null");
        }

        return "(" + Joiner.on(" or ").join(firstClause, secondClause, (Object[]) otherClauses) + ")";
    }

    final String createJpql() {
        Preconditions.checkState(!selectClauses.isEmpty(), "There is no select clause. This is not allowed");
        Preconditions.checkState(!fromClauses.isEmpty(), "There is no from clause. This is not allowed");

        StringBuilder out = new StringBuilder();
        out.append("select ");
        if (distinct) {
            out.append("distinct ");
        }
        Joiner.on(", ").appendTo(out, selectClauses);

        out.append(" from ");
        Joiner.on(" ").appendTo(out, fromClauses);

        if (!whereClauses.isEmpty()) {
            out.append(" where ");
            Joiner.on(" and ").appendTo(out, whereClauses);
        }

        if (!groupByClauses.isEmpty()) {
            out.append(" group by ");
            Joiner.on(", ").appendTo(out, groupByClauses);
        }

        if (!havingClauses.isEmpty()) {
            out.append(" having ");
            Joiner.on(" and ").appendTo(out, havingClauses);
        }

        if (!orderByClauses.isEmpty()) {
            out.append(" order by ");
            Joiner.on(", ").appendTo(out, groupByClauses);
        }

        return out.toString();
    }

    private void bindParameters(Query query) {
        for (ParameterBinder binder : parameters.values()) {
            binder.bind(query);
        }
    }

    /**
     * Interface of the parameter binders
     * @author JB
     */
    private interface ParameterBinder {
        /**
         * Binds the parameter to the query
         * @param query the query
         */
        void bind(Query query);
    }

    /**
     * ParameterBinder implementation for simple (i.e. not temporal) parameters
     * @author JB
     */
    private static final class SimpleParameterBinder implements ParameterBinder {
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
    private static final class DateParameterBinder implements ParameterBinder {
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
     * @author JB
     */
    private static final class CalendarParameterBinder implements ParameterBinder {
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
