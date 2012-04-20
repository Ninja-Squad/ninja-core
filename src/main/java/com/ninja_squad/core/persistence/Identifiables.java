package com.ninja_squad.core.persistence;

import java.util.Comparator;

import javax.annotation.Nonnull;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;

/**
 * Provides Function, Predicate and Comparator for Identifiable instances.
 * @author JB
 */
public final class Identifiables {
    private Identifiables() {
    }

    /**
     * Returns a Predicate which returns <code>true</code> if the Identifiable on which it's applied has the given ID.
     * Determining if a collection of objects contains an object with the ID <code>foo</code> is thus as simple as
     * <code>Iterables.any(collection, Identifiables.withId(foo))</code> for example.
     * @param id the ID that the returned predicate accepts
     * @return the created predicate.
     */
    public static <T> Predicate<Identifiable<T>> withId(@Nonnull final T id) {
        Preconditions.checkNotNull(id, "id may not be null");
        return new Predicate<Identifiable<T>>() {
            @edu.umd.cs.findbugs.annotations.SuppressWarnings(
                value = "NP",
                justification = "will throw an NPE as documented if null")
            @Override
            public boolean apply(Identifiable<T> input) {
                return id.equals(input.getId());
            }
        };
    }

    /**
     * Returns a Predicate which returns <code>true</code> if the Identifiable on which it's applied has the same ID
     * as the given Identifiable.
     * Determining if a collection of objects contains an object with the same ID as <code>foo</code> is thus as simple
     * as <code>Iterables.any(collection, Identifiables.withSameIdAs(foo))</code> for example.
     * @param other an other Identifiable, whose ID may not be <code>null</code>
     * @return the created predicate.
     * @throws NullPointerException if <code>other</code>'s ID is <code>null</code>
     */
    public static <T> Predicate<Identifiable<T>> withSameIdAs(@Nonnull final Identifiable<T> other) {
        Preconditions.checkNotNull(other, "other may not be null");
        Preconditions.checkNotNull(other.getId(), "other's ID may not be null");
        return new Predicate<Identifiable<T>>() {
            @edu.umd.cs.findbugs.annotations.SuppressWarnings(
                value = "NP",
                justification = "will throw an NPE as documented if null")
            @Override
            public boolean apply(Identifiable<T> input) {
                return other.getId().equals(input.getId());
            }
        };
    }

    /**
     * Returns a Function which transforms an Identifiable into its ID.
     * Transforming a collection of Identifiables into the set of their IDs is thus as simple as
     * <code>Sets.newHashSet(Iterables.transform(collection, Identifiables.toId()))</code> for example.
     * @return the created function.
     */
    public static <T> Function<Identifiable<T>, T> toId() {
        return new Function<Identifiable<T>, T>() {
            @edu.umd.cs.findbugs.annotations.SuppressWarnings(
                value = "NP",
                justification = "will throw an NPE as documented if null")
            @Override
            public T apply(Identifiable<T> input) {
                return input.getId();
            }
        };
    }

    /**
     * Returns an Ordering which compares Identifiables by their ID.<br/>
     * Note that the created ordering will throw a NullPointerException if the ID returned by the compared
     * identifiables is <code>null</code>.
     * @return the created ordering.
     */
    @SuppressWarnings("rawtypes")
    public static <I extends Identifiable<? extends Comparable>> Ordering<I> byId() {
        return new Ordering<I>() {
            @edu.umd.cs.findbugs.annotations.SuppressWarnings(
                value = "NP",
                justification = "if null, it'll throw a NPE as documented")
            @SuppressWarnings("unchecked")
            @Override
            public int compare(I left, I right) {
                return left.getId().compareTo(right.getId());
            }
        };
    }

    /**
     * Returns an Ordering which compares Identifiables by their ID using the given ID comparator.<br/>
     * @return the created ordering.
     */
    public static <T, I extends Identifiable<T>> Ordering<I> byId(@Nonnull final Comparator<T> idComparator) {
        Preconditions.checkNotNull(idComparator, "idComparator may not be null");
        return new Ordering<I>() {
            @edu.umd.cs.findbugs.annotations.SuppressWarnings(
                value = "NP",
                justification = "if null, it'll throw a NPE as documented")
            @Override
            public int compare(I left, I right) {
                return idComparator.compare(left.getId(), right.getId());
            }
        };
    }
}
