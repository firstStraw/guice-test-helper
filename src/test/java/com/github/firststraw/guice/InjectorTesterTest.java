package com.github.firststraw.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import nl.jqno.equalsverifier.EqualsVerifier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Tests the {@link InjectorTester} class.
 */
public class InjectorTesterTest {

    private static final AbstractModule MODULE = new AbstractModule() {
        @Override
        protected void configure() {
            // Nothing to do here.
        }
    };

    private static final InjectorTester TESTER = new InjectorTester(MODULE);

    /**
     * Tests the {@link InjectorTester#InjectorTester(Injector)} constructor. Checks that a
     * {@link NullPointerException} is thrown when the {@link Injector} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_WithInjector_NullInjector() {
        new InjectorTester((Injector) null);
    }

    /**
     * Tests the {@link InjectorTester#InjectorTester(Injector)} constructor.
     */
    @Test
    public void testConstructor_WithInjector() {
        assertNotNull(new InjectorTester(Guice.createInjector(Stage.TOOL, MODULE)));
    }

    /**
     * Tests the {@link InjectorTester#InjectorTester(Module...)} constructor. Checks that a
     * {@link NullPointerException} is thrown when the array of {@link Module}s is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_WithModules_NullModuleArray() {
        new InjectorTester((Module[]) null);
    }

    /**
     * Tests the {@link InjectorTester#InjectorTester(Module...)} constructor. Checks that a
     * {@link NullPointerException} is thrown if any of the {@link Module}s are {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_WithModules_NullModule() {
        new InjectorTester(new Module[]{null});
    }

    /**
     * Tests the {@link InjectorTester#verifyBindingFor(Class)} method. Checks that a
     * {@link NullPointerException} is thrown when the type is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testVerifyBindingFor_WithClass_NullClass() {
        TESTER.verifyBindingFor((Class<?>) null);
    }

    /**
     * Tests the {@link InjectorTester#verifyBindingFor(Class)} method. Checks that we get back a
     * {@link BindingTypeVerifier} built with the correct {@link Binding}.
     */
    @Test
    public void testVerifyBindingFor_WithClass() {
        final BindingTypeVerifier<?> typeVerifier = TESTER.verifyBindingFor(Object.class);
        assertEquals(Key.get(Object.class), typeVerifier.getBinding().getKey());
    }

    /**
     * Tests the {@link InjectorTester#verifyBindingFor(TypeLiteral)} method. Checks that a
     * {@link NullPointerException} is thrown when the type is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testVerifyBindingFor_WithTypeLiteral_NullTypeLiteral() {
        TESTER.verifyBindingFor((TypeLiteral<?>) null);
    }

    /**
     * Tests the {@link InjectorTester#verifyBindingFor(TypeLiteral)} method. Checks that we get
     * back a {@link BindingTypeVerifier} built with the correct {@link Binding}.
     */
    @Test
    public void testVerifyBindingFor_WithTypeLiteral() {
        final BindingTypeVerifier<?> typeVerifier =
                TESTER.verifyBindingFor(TypeLiteral.get(Object.class));
        assertEquals(Key.get(Object.class), typeVerifier.getBinding().getKey());
    }

    /**
     * Tests the {@link InjectorTester#verifyBindingFor(Key)} method. Checks that a
     * {@link NullPointerException} is thrown when the {@link Key} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testVerifyBindingFor_WithKey_NullKey() {
        TESTER.verifyBindingFor((Key<?>) null);
    }

    /**
     * Tests the {@link InjectorTester#equals(Object)} and {@link InjectorTester#hashCode()} methods
     * to check whether they conform to their respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedInjectorTester extends InjectorTester {

            ExtendedInjectorTester(final Injector injector) {
                super(injector);
            }

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedInjectorTester;
            }
        }

        EqualsVerifier.forClass(InjectorTester.class)
                .withRedefinedSubclass(ExtendedInjectorTester.class).verify();
    }
}
