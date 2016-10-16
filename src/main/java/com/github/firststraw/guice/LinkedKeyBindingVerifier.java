package com.github.firststraw.guice;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.LinkedKeyBinding;
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Provides methods for verifying that a {@link LinkedKeyBinding} is configured correctly.
 *
 * @param <T> the bound type
 */
@Immutable
public class LinkedKeyBindingVerifier<T> implements BindingVerifier<T> {

    private static final String NULL_EXPECTED_TYPE_ERROR = "Expected type must not be null.";
    private static final String NULL_EXPECTED_KEY_ERROR = "Expected key must not be null.";

    private final LinkedKeyBinding<T> binding;

    /**
     * @param binding the {@link LinkedKeyBinding} to verify
     * @throws NullPointerException if the {@link LinkedKeyBinding} is {@code null}
     */
    LinkedKeyBindingVerifier(final LinkedKeyBinding<T> binding) {
        this.binding = Objects.requireNonNull(binding, "Binding must not be null.");
    }

    /**
     * Verifies that the {@link LinkedKeyBinding} is bound to the correct type.
     *
     * @param expectedType the expected type
     * @return "this" {@link LinkedKeyBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link LinkedKeyBinding} is not bound to the
     * correct type
     */
    public LinkedKeyBindingVerifier<T> withClass(final Class<? extends T> expectedType) {
        Objects.requireNonNull(expectedType, NULL_EXPECTED_TYPE_ERROR);

        return withKey(Key.get(expectedType));
    }

    /**
     * Verifies that the {@link LinkedKeyBinding} is bound to the correct type.
     *
     * @param expectedType the expected type
     * @return "this" {@link LinkedKeyBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link LinkedKeyBinding} is not bound to the
     * correct type
     */
    public LinkedKeyBindingVerifier<T> withTypeLiteral(
            final TypeLiteral<? extends T> expectedType) {
        Objects.requireNonNull(expectedType, NULL_EXPECTED_TYPE_ERROR);

        return withKey(Key.get(expectedType));
    }

    /**
     * Verifies that the {@link LinkedKeyBinding} is bound to the correct {@link Key}.
     *
     * @param expectedKey the expected {@link Key}
     * @return "this" {@link LinkedKeyBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link LinkedKeyBinding} is not bound to the
     * correct {@link Key}
     */
    public LinkedKeyBindingVerifier<T> withKey(final Key<? extends T> expectedKey) {
        Objects.requireNonNull(expectedKey, NULL_EXPECTED_KEY_ERROR);

        final Key<? extends T> key = binding.getLinkedKey();
        if (key.equals(expectedKey)) {
            return this;
        } else {
            throw new IncorrectBindingTargetException(expectedKey, key);
        }
    }

    /**
     * Returns the {@link LinkedKeyBinding} to verify.
     *
     * @return the {@link LinkedKeyBinding} to verify
     */
    @Override
    public LinkedKeyBinding<T> getBinding() {
        return binding;
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof LinkedKeyBindingVerifier;
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
        } else if (obj instanceof LinkedKeyBindingVerifier) {
            final LinkedKeyBindingVerifier<?> rhs = (LinkedKeyBindingVerifier) obj;
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
