package com.github.firststraw.guice;

import com.google.inject.spi.ConstructorBinding;
import com.google.inject.spi.InjectionPoint;
import java.lang.reflect.Constructor;
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Provides methods for verifying that a {@link ConstructorBinding} is configured correctly.
 *
 * @param <T> the bound type
 */
@Immutable
public class ConstructorBindingVerifier<T> implements BindingVerifier<T> {

    private final ConstructorBinding<T> binding;

    /**
     * @param binding the {@link ConstructorBinding} to verify
     * @throws NullPointerException if the {@link ConstructorBinding} is {@code null}
     */
    ConstructorBindingVerifier(final ConstructorBinding<T> binding) {
        this.binding = Objects.requireNonNull(binding, "Binding must not be null.");
    }

    /**
     * Allows any {@link Constructor} for the {@link ConstructorBinding}.
     *
     * @return "this" {@link ConstructorBindingVerifier}
     */
    public ConstructorBindingVerifier<T> withAnyConstructor() {
        return this;
    }

    /**
     * Verifies that the {@link ConstructorBinding} is bound to the correct {@link Constructor}.
     *
     * @param expectedConstructor the expected {@link Constructor}
     * @return "this" {@link ConstructorBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link ConstructorBinding} is bound to a
     * {@link Constructor} other than the expected one
     */
    public ConstructorBindingVerifier<T> withConstructor(
            final Constructor<T> expectedConstructor) {
        Objects.requireNonNull(expectedConstructor, "Expected constructor must not be null.");

        final InjectionPoint constructor = binding.getConstructor();
        if (constructor.equals(InjectionPoint.forConstructor(expectedConstructor))) {
            return this;
        } else {
            throw new IncorrectBindingTargetException(expectedConstructor, constructor.getMember());
        }
    }

    /**
     * Returns the {@link ConstructorBinding} to verify.
     *
     * @return the {@link ConstructorBinding} to verify
     */
    @Override
    public ConstructorBinding<T> getBinding() {
        return binding;
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof ConstructorBindingVerifier;
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
        } else if (obj instanceof ConstructorBindingVerifier) {
            final ConstructorBindingVerifier<?> rhs = (ConstructorBindingVerifier) obj;
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
