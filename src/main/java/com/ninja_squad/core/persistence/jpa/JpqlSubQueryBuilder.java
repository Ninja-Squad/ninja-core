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

/**
 * A query builder returned by the {@link JpqlQueryBuilder#subQueryBuilder()} and
 * {@link JpqlQueryBuilder#distinctSubQueryBuilder()} methods.
 * @author JB Nizet
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
    public JpqlQueryBuilder setParameter(@Nonnull String name, Object value) {
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
    public JpqlQueryBuilder setParameter(@Nonnull String name, Date value, TemporalType temporalType) {
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
    public JpqlQueryBuilder setParameter(@Nonnull String name, Calendar value, TemporalType temporalType) {
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
    public <T> TypedQuery<T> createQuery(@Nonnull EntityManager entityManager, @Nonnull Class<T> resultType) {
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
    public Query createQuery(@Nonnull EntityManager entityManager) {
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

    /**
     * Transforms the sub query into a JPQL clause, wrapped in parentheses, that can be added as a from or where
     * clause to its parent builder.
     * @return the JPQL clause of this sub query builder, wrapped in parentheses.
     */
    public String toWrappedJpql() {
        // spaces around are wanted, to allows for "where foo.bar in" + subQueryBuilder.toWrappedJpql()
        return " (" + createJpql() + ") ";
    }
}
