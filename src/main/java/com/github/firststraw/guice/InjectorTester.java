package com.github.firststraw.guice;

import com.google.inject.Binding;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Convenience class simplifying unit testing of {@link Injector}s.
 */
public class InjectorTester {

    private static final String NULL_INJECTOR_ERROR = "Injector must not be null.";
    private static final String NULL_MODULES_ERROR = "Modules must not be null.";
    private static final String NULL_MODULE_ERROR = "All modules must be non-null.";
    private static final String NULL_TYPE_ERROR = "Type must not be null.";
    private static final String NULL_KEY_ERROR = "Key must not be null.";

    private final Injector injector;

    /**
     * @param injector the {@link Injector} to test
     */
    public InjectorTester(final Injector injector) {
        this.injector = Objects.requireNonNull(injector, NULL_INJECTOR_ERROR);
    }

    /**
     * @param modules {@link Module}s with which to initialize an {@link Injector}
     */
    public InjectorTester(final Module... modules) {
        Objects.requireNonNull(modules, NULL_MODULES_ERROR);
        for (final Module module : modules) {
            Objects.requireNonNull(module, NULL_MODULE_ERROR);
        }

        this.injector = Guice.createInjector(Stage.TOOL, modules);
    }

    /**
     * Verifies that the {@link Injector} has a {@link Binding} for the specified type with no
     * annotation.
     *
     * @param <T> the type
     * @param type the type
     * @return {@link BindingTypeVerifier} for the {@link Binding}
     * @throws NullPointerException if the type is {@code null}
     * @throws ConfigurationException if the {@link Binding} could not be found or created
     */
    public <T> BindingTypeVerifier<T> verifyBindingFor(final Class<T> type) {
        Objects.requireNonNull(type, NULL_TYPE_ERROR);

        return verifyBindingFor(Key.get(type));
    }

    /**
     * Verifies that the {@link Injector} has a {@link Binding} for the specified type with no
     * annotation.
     *
     * @param <T> the type
     * @param type the type
     * @return {@link BindingTypeVerifier} for the {@link Binding}
     * @throws NullPointerException if the type is {@code null}
     * @throws ConfigurationException if the {@link Binding} could not be found or created
     */
    public <T> BindingTypeVerifier<T> verifyBindingFor(final TypeLiteral<T> type) {
        Objects.requireNonNull(type, NULL_TYPE_ERROR);

        return verifyBindingFor(Key.get(type));
    }

    /**
     * Verifies that the {@link Injector} has a {@link Binding} for the specified {@link Key}.
     *
     * @param <T> the type
     * @param key the {@link Key}
     * @return {@link BindingTypeVerifier} for the {@link Binding}
     * @throws NullPointerException if the type is {@code null}
     * @throws ConfigurationException if the {@link Binding} could not be found or created
     */
    public <T> BindingTypeVerifier<T> verifyBindingFor(final Key<T> key) {
        Objects.requireNonNull(key, NULL_KEY_ERROR);

        final Binding<T> binding = injector.getBinding(key);
        return new BindingTypeVerifier<>(binding);
    }

    /**
     * Returns the {@link Injector} being tested.
     *
     * @return the {@link Injector} being tested
     */
    public Injector getInjector() {
        return injector;
    }

    /**
     * Indicates whether the {@link Object} can potentially be equal to "this".
     *
     * @param obj the {@link Object}
     * @return {@code true} if the {@link Object} can potentially be equal to "this"
     */
    protected boolean canEqual(final Object obj) {
        return obj instanceof InjectorTester;
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
        } else if (obj instanceof InjectorTester) {
            final InjectorTester rhs = (InjectorTester) obj;
            return rhs.canEqual(this) && new EqualsBuilder()
                    .append(getInjector(), rhs.getInjector())
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
        return new HashCodeBuilder().append(getInjector()).toHashCode();
    }
}
