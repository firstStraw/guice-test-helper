package com.github.firststraw.guice;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the {@link IncorrectBindingTargetException} class.
 */
public class IncorrectBindingTargetExceptionTest {

    /**
     * Checks that the {@link IncorrectBindingTargetException} is configured correctly.
     */
    @Test
    public void test() {
        final String expected = "expected instance";
        final String found = "found instance";
        final IncorrectBindingTargetException ex =
                new IncorrectBindingTargetException(expected, found);

        assertEquals("Expected " + expected + ", but found " + found, ex.getMessage());
    }
}
