package com.github.firststraw.guice;

import com.google.inject.Binding;

/**
 * Thrown when a {@link Binding} does not have the expected scoping.
 */
public class IncorrectScopingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param expected the expected {@link Scoping}
     * @param actual the actual {@link Scoping}
     */
    IncorrectScopingException(final Scoping expected, final Scoping actual) {
        super("Expected " + expected.getDescription() + ", but found " + actual.getDescription());
    }
}
