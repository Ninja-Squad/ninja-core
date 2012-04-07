package com.ninja_squad.core.persistence.hibernate;

import java.util.Date;

import javax.annotation.Nonnull;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * A query builder returned by the {@link HqlQueryBuilder#subQueryBuilder()} and
 * {@link HqlQueryBuilder#distinctSubQueryBuilder()} methods.
 * @author JB
 */
public class HqlSubQueryBuilder extends HqlQueryBuilder {
    private final HqlQueryBuilder parent;

    HqlSubQueryBuilder(HqlQueryBuilder parent, boolean distinct) {
        super(distinct);
        this.parent = parent;
    }

    /**
     * Sets the parameter on the parent query builder. Setting parameters on the parent or on the
     * sub query builder is thus equivalent.
     *
     * {@inheritDoc}
     */
    @Override
    public HqlQueryBuilder setParameter(@Nonnull String name, Object value) {
        parent.setParameter(name, value);
        return this;
    }

    /**
     * Sets the parameter on the parent query builder. Setting parameters on the parent or on the
     * sub query builder is thus equivalent.
     *
     * {@inheritDoc}
     */
    @Override
    public HqlQueryBuilder setDateParameter(@Nonnull String name, Date value) {
        parent.setDateParameter(name, value);
        return this;
    }

    /**
     * Sets the parameter on the parent query builder. Setting parameters on the parent or on the
     * sub query builder is thus equivalent.
     *
     * {@inheritDoc}
     */
    @Override
    public HqlQueryBuilder setTimestampParameter(@Nonnull String name, Date value) {
        parent.setTimestampParameter(name, value);
        return this;
    }

    /**
     * Sets the parameter on the parent query builder. Setting parameters on the parent or on the
     * sub query builder is thus equivalent.
     *
     * {@inheritDoc}
     */
    @Override
    public HqlQueryBuilder setTimeParameter(@Nonnull String name, Date value) {
        parent.setTimeParameter(name, value);
        return this;
    }

    /**
     * Creates the query on the parent query builder. Creating the query from the parent or from the
     * sub query builder is thus equivalent. It's more logical and readable to call this method on
     * the parent builder though, so calling this method is discouraged.
     *
     * {@inheritDoc}
     */
    @Override
    public Query createQuery(@Nonnull Session session) {
        return parent.createQuery(session);
    }

    /**
     * Transforms the sub query into a JPQL clause, that can be added as a from or where clause to its parent
     * builder.
     * @return the JPQL clause of this sub query builder.
     */
    public String toHql() {
        return createHql();
    }
}
