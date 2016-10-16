package com.github.firststraw.guice;

import com.google.inject.Binding;

/**
 * Verifies a {@link Binding}'s configuration.
 *
 * @param <T> the bound type
 */
@FunctionalInterface
public interface BindingVerifier<T> {

    /**
     * Returns the {@link Binding} being verified.
     *
     * @return the {@link Binding} being verified
     */
    Binding<T> getBinding();

    /**
     * Returns a {@link BindingScopingVerifier} for the {@link Binding}.
     *
     * @return a {@link BindingScopingVerifier} for the {@link Binding}
     */
    default BindingScopingVerifier withScoping() {
        return new BindingScopingVerifier(getBinding());
    }
}
