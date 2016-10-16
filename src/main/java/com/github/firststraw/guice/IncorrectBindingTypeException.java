package com.github.firststraw.guice;

import com.google.inject.Binding;

/**
 * Thrown when a {@link Binding} is of the incorrect type.
 */
public class IncorrectBindingTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param <T> the expected {@link Binding} type
     * @param expectedType the expected {@link Binding} type
     * @param foundBinding the {@link Binding} actually found
     */
    <T extends Binding<?>> IncorrectBindingTypeException(final Class<T> expectedType,
            final Binding<?> foundBinding) {
        super("Expected a binding of type " + expectedType + ", but found binding of type "
                + foundBinding.getClass());
    }
}
