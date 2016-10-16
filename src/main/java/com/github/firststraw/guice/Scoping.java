package com.github.firststraw.guice;

import com.google.inject.Binding;
import com.google.inject.Scope;
import java.lang.annotation.Annotation;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Describes the scoping of a {@link Binding}.
 */
@Immutable
public class Scoping {

    static final String EAGER_SINGLETON = "Eager Singleton";
    static final String NO_SCOPING = "No Scoping";

    private final boolean eagerSingleton;
    private final Scope scope;
    private final Class<? extends Annotation> scopeAnnotation;
    private final boolean noScoping;

    private final String description;

    /**
     * @param eagerSingleton {@code true} if the {@link Binding} is scoped as an eager singleton,
     * otherwise {@code false}
     * @param scope the {@link Binding}'s {@link Scope}, or {@code null} if the {@link Binding} does
     * not have a {@link Scope}
     * @param scopeAnnotation the {@link Binding}'s scope annotation, or {@code null} if the
     * {@link Binding} does not have a scope annotation
     * @param noScoping {@code true} if the {@link Binding} has no scoping, otherwise {@code false}
     * @param description a text scoping of the {@link Binding}'s scoping, or lack thereof
     */
    private Scoping(final boolean eagerSingleton, @Nullable final Scope scope,
            @Nullable final Class<? extends Annotation> scopeAnnotation, final boolean noScoping,
            final String description) {
        this.eagerSingleton = eagerSingleton;
        this.scope = scope;
        this.scopeAnnotation = scopeAnnotation;
        this.noScoping = noScoping;
        this.description = description;
    }

    /**
     * Returns a {@link Scoping} describing eager singleton scoping.
     *
     * @return a {@link Scoping} describing eager singleton scoping
     */
    public static Scoping eagerSingleton() {
        return new Scoping(true, null, null, false, EAGER_SINGLETON);
    }

    /**
     * Returns a {@link Scoping} describing scoping using a specific {@link Scope}.
     *
     * @param scope the {@link Scope}
     * @return a {@link Scoping} describing scoping using a specific {@link Scope}
     */
    public static Scoping scope(final Scope scope) {
        Objects.requireNonNull(scope, "Scope must not be null.");

        return new Scoping(false, scope, null, false, scope.toString());
    }

    /**
     * Returns a {@link Scoping} describing scoping using a specific scope annotation.
     *
     * @param scopeAnnotation the scope annotation
     * @return a {@link Scoping} describing scoping using a specific scope annotation
     */
    public static Scoping scopeAnnotation(final Class<? extends Annotation> scopeAnnotation) {
        Objects.requireNonNull(scopeAnnotation, "Scope annotation must not be null.");

        return new Scoping(false, null, scopeAnnotation, false, scopeAnnotation.toString());
    }

    /**
     * Returns a {@link Scoping} describing a lack of scoping.
     *
     * @return a {@link Scoping} describing a lack of scoping
     */
    public static Scoping noScoping() {
        return new Scoping(false, null, null, true, NO_SCOPING);
    }

    /**
     * Returns {@code true} if the {@link Binding} has eager singleton scoping, otherwise
     * {@code false}.
     *
     * @return {@code true} if the {@link Binding} has eager singleton scoping, otherwise
     * {@code false}
     */
    public boolean isEagerSingleton() {
        return eagerSingleton;
    }

    /**
     * Returns the {@link Binding}'s {@link Scope}, or {@code null} if the {@link Binding} has no
     * {@link Scope}.
     *
     * @return the {@link Binding}'s {@link Scope}, or {@code null} if the {@link Binding} has no
     * {@link Scope}
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Returns the {@link Binding}'s scope annotation, or {@code null} if the {@link Binding} has no
     * scope annotation.
     *
     * @return the {@link Binding}'s scope annotation, or {@code null} if the {@link Binding} has no
     * scope annotation
     */
    public Class<? extends Annotation> getScopeAnnotation() {
        return scopeAnnotation;
    }

    /**
     * Returns {@code true} if the {@link Binding} has no scoping, otherwise {@code false}.
     *
     * @return {@code true} if the {@link Binding} has no scoping, otherwise {@code false}
     */
    public boolean isNoScoping() {
        return noScoping;
    }

    /**
     * Returns a text description of the scoping for the {@link Binding}.
     *
     * @return a text description of the scoping for the {@link Binding}
     */
    public String getDescription() {
        return description;
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
        } else if (obj instanceof Scoping) {
            final Scoping rhs = (Scoping) obj;
            return new EqualsBuilder()
                    .append(isEagerSingleton(), rhs.isEagerSingleton())
                    .append(getScope(), rhs.getScope())
                    .append(getScopeAnnotation(), rhs.getScopeAnnotation())
                    .append(isNoScoping(), rhs.isNoScoping())
                    .append(getDescription(), rhs.getDescription())
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
        return new HashCodeBuilder()
                .append(isEagerSingleton())
                .append(getScope())
                .append(getScopeAnnotation())
                .append(isNoScoping())
                .append(getDescription())
                .toHashCode();
    }
}
