package com.ninja_squad.core.persistence;

/**
 * <p>
 * Interface of an object (typically, a JPA or Hibernate entity, but it could also be a DTO) that has an ID.
 * </p>
 * <p>
 * Making objects implement the Identifiable interface allows easily tranforming, using the {@link Identifiables}
 * utility class, a collection of objects into a collection of their IDs. It also allows finding and sorting such
 * objects by their ID.
 * @author JB
 *
 * @param <T> the type of ID of the object.
 */
public interface Identifiable<T> {

    /**
     * Gets the ID of the object.
     * @return the ID of the object.
     */
    T getId();
}
