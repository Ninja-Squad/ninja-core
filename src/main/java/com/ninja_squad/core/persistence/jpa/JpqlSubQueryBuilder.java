package com.ninja_squad.core.persistence.jpa;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 * A query builder returned by the {@link JpqlQueryBuilder#subQueryBuilder()} and
 * {@link JpqlQueryBuilder#distinctSubQueryBuilder()} methods.
 * @author JB
 */
public class JpqlSubQueryBuilder extends JpqlQueryBuilder {
    private final JpqlQueryBuilder parent;

    JpqlSubQueryBuilder(JpqlQueryBuilder parent, boolean distinct) {
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
    public JpqlQueryBuilder setParameter(String name, Object value) {
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
    public JpqlQueryBuilder setParameter(String name, Date value, TemporalType temporalType) {
        parent.setParameter(name, value, temporalType);
        return this;
    }

    /**
     * Sets the parameter on the parent query builder. Setting parameters on the parent or on the
     * sub query builder is thus equivalent.
     *
     * {@inheritDoc}
     */
    @Override
    public JpqlQueryBuilder setParameter(String name, Calendar value, TemporalType temporalType) {
        parent.setParameter(name, value, temporalType);
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
    public <T> TypedQuery<T> createQuery(EntityManager entityManager, Class<T> resultType) {
        return parent.createQuery(entityManager, resultType);
    }

    /**
     * Creates the query on the parent query builder. Creating the query from the parent or from the
     * sub query builder is thus equivalent. It's more logical and readable to call this method on
     * the parent builder though, so calling this method is discouraged.
     *
     * {@inheritDoc}
     */
    @Override
    public Query createQuery(EntityManager entityManager) {
        return parent.createQuery(entityManager);
    }

    /**
     * Transforms the sub query into a JPQL clause, that can be added as a from or where clause to its parent
     * builder.
     * @return the JPQL clause of this sub query builder.
     */
    public String toJpql() {
        return createJpql();
    }
}
