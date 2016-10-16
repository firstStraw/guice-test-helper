package com.github.firststraw.guice;

import com.google.inject.spi.InstanceBinding;
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Provides methods for verifying that an {@link InstanceBinding} is configured correctly.
 *
 * @param <T> the bound type
 */
@Immutable
public class InstanceBindingVerifier<T> implements BindingVerifier<T> {

    private static final String NULL_EXPECTED_INSTANCE_ERROR =
            "Expected instance must not be null.";

    private final InstanceBinding<T> binding;

    /**
     * @param binding the {@link InstanceBinding} to verify
     * @throws NullPointerException if the {@link InstanceBinding} is {@code null}
     */
    InstanceBindingVerifier(final InstanceBinding<T> binding) {
        this.binding = Objects.requireNonNull(binding, "Binding must not be null.");
    }

    /**
     * Verifies that the {@link InstanceBinding} is bound to the correct instance.
     *
     * @param expectedInstance the expected instance
     * @return "this" {@link InstanceBindingVerifier}
     * @throws IncorrectBindingTargetException if the instance is not the same object as the
     * expected instance
     */
    public InstanceBindingVerifier<T> withInstance(final T expectedInstance) {
        Objects.requireNonNull(expectedInstance, NULL_EXPECTED_INSTANCE_ERROR);

        final T instance = binding.getInstance();
        if (expectedInstance == instance) {
            return this;
        } else {
            throw new IncorrectBindingTargetException(expectedInstance, instance);
        }
    }

    /**
     * Verifies that the {@link InstanceBinding} is bound to an instance that is equal to the
     * expected instance.
     *
     * @param expectedInstance the expected instance
     * @return "this" {@link InstanceBindingVerifier}
     * @throws IncorrectBindingTargetException if the instance is not equal to the expected instance
     */
    public InstanceBindingVerifier<T> withEqualInstance(final T expectedInstance) {
        Objects.requireNonNull(expectedInstance, NULL_EXPECTED_INSTANCE_ERROR);

        final T instance = binding.getInstance();
        if (expectedInstance.equals(instance)) {
            return this;
        } else {
            throw new IncorrectBindingTargetException(expectedInstance, instance);
        }
    }

    /**
     * Returns the {@link InstanceBinding} to verify.
     *
     * @return the {@link InstanceBinding} to verify
     */
    @Override
    public InstanceBinding<T> getBinding() {
        return binding;
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof InstanceBindingVerifier;
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
        } else if (obj instanceof InstanceBindingVerifier) {
            final InstanceBindingVerifier<?> rhs = (InstanceBindingVerifier) obj;
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
