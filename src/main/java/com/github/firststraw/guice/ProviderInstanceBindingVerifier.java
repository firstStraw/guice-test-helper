package com.github.firststraw.guice;

import com.google.inject.spi.ProviderInstanceBinding;
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import javax.inject.Provider;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Provides methods for verifying that a {@link ProviderInstanceBinding} is configured correctly.
 *
 * @param <T> the bound type
 */
@Immutable
public class ProviderInstanceBindingVerifier<T> implements BindingVerifier<T> {

    private static final String NULL_EXPECTED_PROVIDER_ERROR =
            "Expected provider must not be null.";

    private final ProviderInstanceBinding<T> binding;

    /**
     * @param binding the {@link ProviderInstanceBinding} to verify
     * @throws NullPointerException if the {@link ProviderInstanceBinding} is {@code null}
     */
    ProviderInstanceBindingVerifier(final ProviderInstanceBinding<T> binding) {
        this.binding = Objects.requireNonNull(binding, "Binding must not be null.");
    }

    /**
     * Verifies that the {@link ProviderInstanceBinding} is bound to the correct {@link Provider}.
     *
     * @param expectedProvider the expected {@link Provider}
     * @return "this" {@link ProviderInstanceBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link ProviderInstanceBinding} is bound to a
     * {@link Provider} other than the expected one
     */
    public ProviderInstanceBindingVerifier<T> withProvider(
            final Provider<? extends T> expectedProvider) {
        Objects.requireNonNull(expectedProvider, NULL_EXPECTED_PROVIDER_ERROR);

        final Provider<? extends T> provider = binding.getUserSuppliedProvider();
        if (provider == expectedProvider) {
            return this;
        } else {
            throw new IncorrectBindingTargetException(expectedProvider, provider);
        }
    }

    /**
     * Verifies that the {@link ProviderInstanceBinding} is bound to an equal {@link Provider}.
     *
     * @param expectedProvider the expected {@link Provider}
     * @return "this" {@link ProviderInstanceBindingVerifier}
     * @throws IncorrectBindingTargetException if the {@link ProviderInstanceBinding} is bound to a
     * {@link Provider} not equal to the expected one
     */
    public ProviderInstanceBindingVerifier<T> withEqualProvider(
            final Provider<? extends T> expectedProvider) {
        Objects.requireNonNull(expectedProvider, NULL_EXPECTED_PROVIDER_ERROR);

        final Provider<? extends T> provider = binding.getUserSuppliedProvider();
        if (provider.equals(expectedProvider)) {
            return this;
        } else {
            throw new IncorrectBindingTargetException(expectedProvider, provider);
        }
    }

    /**
     * Returns the {@link ProviderInstanceBinding} to verify.
     *
     * @return the {@link ProviderInstanceBinding} to verify
     */
    @Override
    public ProviderInstanceBinding<T> getBinding() {
        return binding;
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof ProviderInstanceBindingVerifier;
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
        } else if (obj instanceof ProviderInstanceBindingVerifier) {
            final ProviderInstanceBindingVerifier<?> rhs = (ProviderInstanceBindingVerifier) obj;
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
