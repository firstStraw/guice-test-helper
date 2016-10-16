package com.github.firststraw.guice;

import com.google.inject.Binding;
import com.google.inject.Scope;
import java.lang.annotation.Annotation;
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Verifies that a {@link Binding} has the correct scoping.
 */
@Immutable
public class BindingScopingVerifier {

    private final Binding<?> binding;

    /**
     * @param binding the {@link Binding} to verify the scoping of
     */
    BindingScopingVerifier(final Binding<?> binding) {
        this.binding = Objects.requireNonNull(binding, "Binding must not be null.");
    }

    /**
     * Verifies that the {@link Binding} has eager singleton scoping.
     *
     * @throws IncorrectScopingException if the {@link Binding} does not have eager singleton
     * scoping
     */
    public void eagerSingleton() {
        final Scoping scoping = getScoping();
        if (!scoping.isEagerSingleton()) {
            throw new IncorrectScopingException(Scoping.eagerSingleton(), scoping);
        }
    }

    /**
     * Verifies that the {@link Binding} has a specific {@link Scope}.
     *
     * @param scope the {@link Scope} to check for
     * @throws NullPointerException if the {@link Scope} is {@code null}
     * @throws IncorrectScopingException if the {@link Binding}'s scoping does not match the
     * {@link Scope}
     */
    public void scope(final Scope scope) {
        Objects.requireNonNull(scope, "Scope must not be null.");

        final Scoping scoping = getScoping();
        if (!scope.equals(scoping.getScope())) {
            throw new IncorrectScopingException(Scoping.scope(scope), scoping);
        }
    }

    /**
     * Verifies that the {@link Binding} has a specific scope annotation.
     *
     * @param scopeAnnotation the scope annotation to check for
     * @throws NullPointerException if the scope annotation is {@code null}
     * @throws IncorrectScopingException if the {@link Binding}'s scoping does not match the scope
     * annotation
     */
    public void scopeAnnotation(final Class<? extends Annotation> scopeAnnotation) {
        Objects.requireNonNull(scopeAnnotation, "Scope annotation must not be null.");

        final Scoping scoping = getScoping();
        if (!scopeAnnotation.equals(scoping.getScopeAnnotation())) {
            throw new IncorrectScopingException(Scoping.scopeAnnotation(scopeAnnotation), scoping);
        }
    }

    /**
     * Verifies that the {@link Binding} has no scoping.
     *
     * @throws IncorrectScopingException if the {@link Binding} has scoping
     */
    public void noScoping() {
        final Scoping scoping = getScoping();
        if (!scoping.isNoScoping()) {
            throw new IncorrectScopingException(Scoping.noScoping(), scoping);
        }
    }

    /**
     * Returns a {@link Scoping} describing the scoping of the {@link Binding}.
     *
     * @return a {@link Scoping} describing the scoping of the {@link Binding}
     */
    private Scoping getScoping() {
        return binding.acceptScopingVisitor(new ScopingRecorder());
    }

    /**
     * Returns the {@link Binding} to verify the scoping of.
     *
     * @return the {@link Binding} to verify the scoping of
     */
    private Binding<?> getBinding() {
        return binding;
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof BindingScopingVerifier;
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
        } else if (obj instanceof BindingScopingVerifier) {
            final BindingScopingVerifier rhs = (BindingScopingVerifier) obj;
            return rhs.canEqual(this) && new EqualsBuilder()
                    .append(getBinding(), rhs.getBinding())
                    .isEquals();
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
        return new HashCodeBuilder().append(getBinding()).toHashCode();
    }
}
