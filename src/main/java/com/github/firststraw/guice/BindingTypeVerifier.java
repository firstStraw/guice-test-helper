package com.github.firststraw.guice;

import com.google.inject.Binding;
import com.google.inject.spi.ConstructorBinding;
import com.google.inject.spi.InstanceBinding;
import com.google.inject.spi.LinkedKeyBinding;
import com.google.inject.spi.ProviderInstanceBinding;
import com.google.inject.spi.ProviderKeyBinding;
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Checks a {@link Binding}'s type and returns the appropriate {@link BindingVerifier}
 * implementation.
 *
 * @param <T> the bound type
 */
@Immutable
public class BindingTypeVerifier<T> {

    private final Binding<T> binding;

    /**
     * @param binding the {@link Binding} for which to verify the type
     *
     * @throws NullPointerException if the {@link Binding} is {@code null}
     */
    BindingTypeVerifier(final Binding<T> binding) {
        this.binding = Objects.requireNonNull(binding, "Binding must not be null.");
    }

    /**
     * Verifies that the {@link Binding} is a {@link ConstructorBinding}.
     *
     * @return a {@link ConstructorBindingVerifier} for the {@link Binding}
     * @throws IncorrectBindingTypeException if the {@link Binding} is not a
     * {@link ConstructorBinding}
     */
    public ConstructorBindingVerifier<T> asConstructorBinding() {
        if (binding instanceof ConstructorBinding) {
            return new ConstructorBindingVerifier<>((ConstructorBinding<T>) binding);
        } else {
            throw new IncorrectBindingTypeException(ConstructorBinding.class, binding);
        }
    }

    /**
     * Verifies that the {@link Binding} is an {@link InstanceBinding}.
     *
     * @return an {@link InstanceBindingVerifier} for the {@link Binding}
     * @throws IncorrectBindingTypeException if the {@link Binding} is not an
     * {@link InstanceBinding}
     */
    public InstanceBindingVerifier<T> asInstanceBinding() {
        if (binding instanceof InstanceBinding) {
            return new InstanceBindingVerifier<>((InstanceBinding<T>) binding);
        } else {
            throw new IncorrectBindingTypeException(InstanceBinding.class, binding);
        }
    }

    /**
     * Verifies that the {@link Binding} is a {@link LinkedKeyBinding}.
     *
     * @return a {@link LinkedKeyBindingVerifier} for the {@link Binding}
     * @throws IncorrectBindingTypeException if the {@link Binding} is not a
     * {@link LinkedKeyBinding}
     */
    public LinkedKeyBindingVerifier<T> asLinkedKeyBinding() {
        if (binding instanceof LinkedKeyBinding) {
            return new LinkedKeyBindingVerifier<>((LinkedKeyBinding<T>) binding);
        } else {
            throw new IncorrectBindingTypeException(LinkedKeyBinding.class, binding);
        }
    }

    /**
     * Verifies that the {@link Binding} is a {@link ProviderInstanceBinding}.
     *
     * @return a {@link ProviderInstanceBindingVerifier} for the {@link Binding}
     * @throws IncorrectBindingTypeException if the {@link Binding} is not a
     * {@link ProviderInstanceBinding}
     */
    public ProviderInstanceBindingVerifier<T> asProviderInstanceBinding() {
        if (binding instanceof ProviderInstanceBinding) {
            return new ProviderInstanceBindingVerifier<>((ProviderInstanceBinding<T>) binding);
        } else {
            throw new IncorrectBindingTypeException(ProviderInstanceBinding.class, binding);
        }
    }

    /**
     * Verifies that the {@link Binding} is a {@link ProviderKeyBinding}.
     *
     * @return a {@link ProviderKeyBindingVerifier} for the {@link Binding}
     * @throws IncorrectBindingTypeException if the {@link Binding} is not a
     * {@link ProviderKeyBinding}
     */
    public ProviderKeyBindingVerifier<T> asProviderKeyBinding() {
        if (binding instanceof ProviderKeyBinding) {
            return new ProviderKeyBindingVerifier<>((ProviderKeyBinding<T>) binding);
        } else {
            throw new IncorrectBindingTypeException(ProviderKeyBinding.class, binding);
        }
    }

    /**
     * Returns the {@link Binding} for which to verify the type.
     *
     * @return the {@link Binding} for which to verify the type
     */
    public Binding<T> getBinding() {
        return binding;
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof BindingTypeVerifier;
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
        } else if (obj instanceof BindingTypeVerifier) {
            final BindingTypeVerifier<?> rhs = (BindingTypeVerifier) obj;
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
