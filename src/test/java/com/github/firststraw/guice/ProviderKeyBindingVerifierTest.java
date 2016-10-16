package com.github.firststraw.guice;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.ProviderKeyBinding;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.After;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests the {@link ProviderKeyBindingVerifier} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProviderKeyBindingVerifierTest {

    private static final Key<? extends Provider<?>> KEY = Key.get(MyProvider1.class);

    @Mock
    private ProviderKeyBinding<Object> binding;

    private ProviderKeyBindingVerifier<Object> verifier;

    /**
     * Invoked by JUnit prior to each test.
     */
    @Before
    public void setUp() {
        verifier = new ProviderKeyBindingVerifier<>(binding);

        when(binding.getProviderKey()).thenAnswer(invocation -> KEY);
    }

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(binding);
    }

    /**
     * Tests the {@link ProviderKeyBindingVerifier#ProviderKeyBindingVerifier(ProviderKeyBinding)}
     * constructor. Checks that a {@link NullPointerException} is thrown when the
     * {@link ProviderKeyBinding} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_NullBinding() {
        new ProviderKeyBindingVerifier<>(null);
    }

    /**
     * Tests the {@link ProviderKeyBindingVerifier#withClass(Class)} method. Checks that a
     * {@link NullPointerException} is thrown when the {@link Class} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithClass_NullClass() {
        verifier.withClass(null);
    }

    /**
     * Tests the {@link ProviderKeyBindingVerifier#withClass(Class)} method. Checks that we get back
     * the {@link ProviderKeyBindingVerifier}.
     */
    @Test
    public void testWithClass() {
        assertSame(verifier, verifier.withClass(MyProvider1.class));

        verify(binding).getProviderKey();
    }

    /**
     * Tests the {@link ProviderKeyBindingVerifier#withClass(Class)} method. Checks that an
     * {@link IncorrectBindingTargetException} is thrown when the {@link Class} is not the expected
     * one.
     */
    @Test
    public void testWithClass_WrongClass() {
        try {
            assertSame(verifier, verifier.withClass(MyProvider2.class));
            fail("Should have thrown an IncorrectBindingTargetException.");
        } catch (final IncorrectBindingTargetException expection) {
            verify(binding).getProviderKey();
        }
    }

    /**
     * Tests the {@link ProviderKeyBindingVerifier#withTypeLiteral(TypeLiteral)} method. Checks that
     * a {@link NullPointerException} is thrown when the {@link TypeLiteral} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithTypeLiteral_NullTypeLiteral() {
        verifier.withTypeLiteral(null);
    }

    /**
     * Tests the {@link ProviderKeyBindingVerifier#withTypeLiteral(TypeLiteral)} method. Checks that
     * we get back the {@link ProviderKeyBindingVerifier}.
     */
    @Test
    public void testWithTypeLiteral() {
        assertSame(verifier, verifier.withTypeLiteral(TypeLiteral.get(MyProvider1.class)));

        verify(binding).getProviderKey();
    }

    /**
     * Tests the {@link ProviderKeyBindingVerifier#withKey(Key)} method. Checks that a
     * {@link NullPointerException} is thrown when the {@link Key} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithKey_NullKey() {
        verifier.withKey(null);
    }

    /**
     * Tests the {@link ProviderKeyBindingVerifier#equals(Object)} and
     * {@link ProviderKeyBindingVerifier#hashCode()} methods to check whether they conform to their
     * respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedProviderKeyBindingVerifier<T> extends ProviderKeyBindingVerifier<T> {

            ExtendedProviderKeyBindingVerifier(final ProviderKeyBinding<T> binding) {
                super(binding);
            }

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedProviderKeyBindingVerifier;
            }
        }

        EqualsVerifier.forClass(ProviderKeyBindingVerifier.class)
                .withRedefinedSubclass(ExtendedProviderKeyBindingVerifier.class).verify();
    }

    /**
     * {@link Provider} implementation for use in unit testing.
     */
    private static class MyProvider1 implements Provider<Object> {

        @Override
        public Object get() {
            return new Object();
        }
    }

    /**
     * {@link Provider} implementation for use in unit testing.
     */
    private static class MyProvider2 implements Provider<Object> {

        @Override
        public Object get() {
            return new Object();
        }
    }
}
