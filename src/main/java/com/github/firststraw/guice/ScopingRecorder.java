package com.github.firststraw.guice;

import com.google.inject.Binding;
import com.google.inject.Scope;
import com.google.inject.spi.BindingScopingVisitor;
import java.lang.annotation.Annotation;
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * {@link BindingScopingVisitor} implementation that records the {@link Binding}'s scoping
 * information and returns it as a {@link Scoping} instance.
 */
@Immutable
public class ScopingRecorder implements BindingScopingVisitor<Scoping> {

    /**
     * Returns a {@link Scoping} configured for eager singleton scoping.
     *
     * @return a {@link Scoping} configured for eager singleton scoping
     */
    @Override
    public Scoping visitEagerSingleton() {
        return Scoping.eagerSingleton();
    }

    /**
     * Returns a {@link Scoping} configured for a specific {@link Scope}.
     *
     * @param scope the {@link Scope}
     * @return a {@link Scoping} configured for a specific {@link Scope}
     */
    @Override
    public Scoping visitScope(final Scope scope) {
        Objects.requireNonNull(scope, "Scope must not be null.");

        return Scoping.scope(scope);
    }

    /**
     * Returns a {@link Scoping} configured for a specific scope annotation.
     *
     * @param scopeAnnotation the scope annotation
     * @return a {@link Scoping} configured for a specific scope annotation
     */
    @Override
    public Scoping visitScopeAnnotation(final Class<? extends Annotation> scopeAnnotation) {
        Objects.requireNonNull(scopeAnnotation, "Scope annotation must not be null.");

        return Scoping.scopeAnnotation(scopeAnnotation);
    }

    /**
     * Returns a {@link Scoping} configured for the lack of any scoping.
     *
     * @return a {@link Scoping} configured for the lack of any scoping
     */
    @Override
    public Scoping visitNoScoping() {
        return Scoping.noScoping();
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof ScopingRecorder;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false}
     * otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof ScopingRecorder) {
            final ScopingRecorder rhs = (ScopingRecorder) obj;
            return rhs.canEqual(this);
        } else {
            return false;
        }
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().toHashCode();
    }
}
