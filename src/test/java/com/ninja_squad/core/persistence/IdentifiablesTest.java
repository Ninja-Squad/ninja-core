package com.ninja_squad.core.persistence;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

public class IdentifiablesTest {

    private Iterable<TestEntity> entities;

    @Before
    public void prepare() {
        entities = Arrays.asList(new TestEntity(3L),
                                 new TestEntity(2L),
                                 new TestEntity(1L));
    }

    @Test
    public void withIdWorks() {
        TestEntity t2 = Iterables.find(entities, Identifiables.withId(2L));
        assertEquals(2L, t2.getId().longValue());
    }

    @Test
    public void withSameIdAsWorks() {
        // it's supposed to work with a different kind of Identifiable.
        TestEntity t2 = Iterables.find(entities, Identifiables.withSameIdAs(new Identifiable<Long>() {
            @Override
            public Long getId() {
                return 2L;
            }
        }));
        assertEquals(2L, t2.getId().longValue());
    }

    @Test
    public void toIdWorks() {
        Set<Long> ids = Sets.newHashSet(Iterables.transform(entities, Identifiables.<Long>toId()));
        assertEquals(Sets.newHashSet(1L, 2L, 3L), ids);
    }

    @Test
    public void byIdWorks() {
        List<TestEntity> sortedEntities = Lists.newArrayList(entities);
        Collections.sort(sortedEntities, Identifiables.byId());
        assertEquals(1L, sortedEntities.get(0).getId().longValue());
        assertEquals(2L, sortedEntities.get(1).getId().longValue());
        assertEquals(3L, sortedEntities.get(2).getId().longValue());
    }

    @Test
    public void byIdWithComparatorWorks() {
        List<TestEntity> sortedEntities = Lists.newArrayList(entities);
        sortedEntities.add(new TestEntity(null));

        Collections.sort(sortedEntities, Identifiables.byId(Ordering.<Long>natural().nullsFirst()));
        assertNull(sortedEntities.get(0).getId());
        assertEquals(1L, sortedEntities.get(1).getId().longValue());
        assertEquals(2L, sortedEntities.get(2).getId().longValue());
        assertEquals(3L, sortedEntities.get(3).getId().longValue());
    }

    private static class TestEntity implements Identifiable<Long> {

        private Long id;

        public TestEntity(Long id) {
            this.id = id;
        }

        @Override
        public Long getId() {
            return id;
        }
    }
}
