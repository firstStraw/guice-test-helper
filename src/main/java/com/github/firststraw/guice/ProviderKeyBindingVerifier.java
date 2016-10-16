package com.github.firststraw.guice;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.ProviderKeyBinding;
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import javax.inject.Provider;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Provides methods for verifying that a {@link ProviderKeyBinding} is configured correctly.
 *
 * @param <T> the bound type
 */
@Immutable
public class ProviderKeyBindingVerifier<T> implements BindingVerifier<T> {

    private static final String NULL_EXPECTED_TYPE_ERROR = "Expected type must not be null.";
    private static final String NULL_EXPECTED_KEY_ERROR = "Expected key must not be null.";

    private final ProviderKeyBinding<T> binding;

    /**
     * @param binding the {@link ProviderKeyBinding} to verify
     * @throws NullPointerException if the {@link ProviderKeyBinding} is {@code null}
     */
    ProviderKeyBindingVerifier(final ProviderKeyBinding<T> binding) {
        this.binding = Objects.requireNonNull(binding, "Binding must not be null.");
    }

    /**
     * Verifies that the {@link ProviderKeyBinding} is bound to the correct type.
     *
     * @param expectedType the expected type
     * @return "this" {@link ProviderKeyBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link ProviderKeyBinding} is not bound to the
     * correct type
     */
    public ProviderKeyBindingVerifier<T> withClass(
            final Class<? extends Provider<? extends T>> expectedType) {
        Objects.requireNonNull(expectedType, NULL_EXPECTED_TYPE_ERROR);

        return withKey(Key.get(expectedType));
    }

    /**
     * Verifies that the {@link ProviderKeyBinding} is bound to the correct type.
     *
     * @param expectedType the expected type
     * @return "this" {@link ProviderKeyBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link ProviderKeyBinding} is not bound to the
     * correct type
     */
    public ProviderKeyBindingVerifier<T> withTypeLiteral(
            final TypeLiteral<? extends Provider<? extends T>> expectedType) {
        Objects.requireNonNull(expectedType, NULL_EXPECTED_TYPE_ERROR);

        return withKey(Key.get(expectedType));
    }

    /**
     * Verifies that the {@link ProviderKeyBinding} is bound to the correct {@link Key}.
     *
     * @param expectedKey the expected {@link Key}
     * @return "this" {@link ProviderKeyBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link ProviderKeyBinding} is not bound to the
     * correct {@link Key}
     */
    public ProviderKeyBindingVerifier<T> withKey(
            final Key<? extends Provider<? extends T>> expectedKey) {
        Objects.requireNonNull(expectedKey, NULL_EXPECTED_KEY_ERROR);

        final Key<? extends Provider<? extends T>> key = binding.getProviderKey();
        if (key.equals(expectedKey)) {
            return this;
        } else {
            throw new IncorrectBindingTargetException(expectedKey, key);
        }
    }

    /**
     * Returns the {@link ProviderKeyBinding} to verify.
     *
     * @return the {@link ProviderKeyBinding} to verify
     */
    @Override
    public ProviderKeyBinding<T> getBinding() {
        return binding;
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof ProviderKeyBindingVerifier;
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
        } else if (obj instanceof ProviderKeyBindingVerifier) {
            final ProviderKeyBindingVerifier<?> rhs = (ProviderKeyBindingVerifier) obj;
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
