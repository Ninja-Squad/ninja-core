package com.ninja_squad.core.base;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class OptionalsTest {
    @Test
    public void toOptionalWorks() {
        List<String> strings = Lists.newArrayList("A", null, "C");
        ImmutableList<Optional<String>> immutableStrings =
            ImmutableList.copyOf(Iterables.transform(strings, Optionals.<String>toOptional()));
        assertEquals("A", immutableStrings.get(0).get());
        assertFalse(immutableStrings.get(1).isPresent());
        assertEquals("C", immutableStrings.get(2).get());
    }
}
