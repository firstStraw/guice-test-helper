package com.github.firststraw.guice;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the {@link IncorrectScopingException} class.
 */
public class IncorrectScopingExceptionTest {

    /**
     * Checks that the {@link IncorrectScopingException} is configured correctly.
     */
    @Test
    public void test() {
        final Scoping expected = Scoping.eagerSingleton();
        final Scoping found = Scoping.noScoping();
        final IncorrectScopingException ex = new IncorrectScopingException(expected, found);

        final String msg = "Expected " + expected.getDescription() + ", but found "
                + found.getDescription();
        assertEquals(msg, ex.getMessage());
    }
}
