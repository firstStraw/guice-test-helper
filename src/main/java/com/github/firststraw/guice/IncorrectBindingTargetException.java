package com.github.firststraw.guice;

import com.google.inject.Binding;

/**
 * Thrown when a {@link Binding} is not bound to the expected target.
 */
public class IncorrectBindingTargetException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param expectedTarget the expected target
     * @param actualTarget the actual target
     */
    IncorrectBindingTargetException(final Object expectedTarget, final Object actualTarget) {
        super("Expected " + expectedTarget + ", but found " + actualTarget);
    }
}
