package com.github.firststraw.guice;

import com.google.inject.spi.ProviderInstanceBinding;
import javax.inject.Provider;
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
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests the {@link ProviderInstanceBindingVerifier} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProviderInstanceBindingVerifierTest {

    private static final MyProvider PROVIDER = new MyProvider(1);

    @Mock
    private ProviderInstanceBinding<Object> binding;

    private ProviderInstanceBindingVerifier<Object> verifier;

    /**
     * Invoked by JUnit prior to each test.
     */
    @Before
    public void setUp() {
        verifier = new ProviderInstanceBindingVerifier<>(binding);

        when(binding.getUserSuppliedProvider()).thenAnswer(invocation -> PROVIDER);
    }

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(binding);
    }

    /**
     * Tests the
     * {@link ProviderInstanceBindingVerifier#ProviderInstanceBindingVerifier(ProviderInstanceBinding)}
     * constructor. Checks that a {@link NullPointerException} is thrown when the
     * {@link ProviderInstanceBinding} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_NullBinding() {
        new ProviderInstanceBindingVerifier<>(null);
    }

    /**
     * Tests the {@link ProviderInstanceBindingVerifier#withProvider(Provider)} method. Checks that
     * a {@link NullPointerException} is thrown when the {@link Provider} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithProvider_NullProvider() {
        verifier.withProvider(null);
    }

    /**
     * Tests the {@link ProviderInstanceBindingVerifier#withProvider(Provider)} method. Checks that
     * we get back the {@link ProviderInstanceBindingVerifier}.
     */
    @Test
    public void testWithProvider() {
        assertSame(verifier, verifier.withProvider(PROVIDER));

        verify(binding).getUserSuppliedProvider();
    }

    /**
     * Tests the {@link ProviderInstanceBindingVerifier#withProvider(Provider)} method. Checks that
     * a {@link IncorrectBindingTargetException} is thrown when the {@link ProviderInstanceBinding}
     * is bound to a {@link Provider} other than the expected one.
     */
    @Test
    public void testWithProvider_WrongProvider() {
        try {
            verifier.withProvider(new MyProvider(1));
            fail("Should have thrown an IncorrectBindingTargetException.");
        } catch (final IncorrectBindingTargetException expected) {
            verify(binding).getUserSuppliedProvider();
        }
    }

    /**
     * Tests the {@link ProviderInstanceBindingVerifier#withEqualProvider(Provider)} method. Checks
     * that a {@link NullPointerException} is thrown when the {@link Provider} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithEqualProvider_NullProvider() {
        verifier.withEqualProvider(null);
    }

    /**
     * Tests the {@link ProviderInstanceBindingVerifier#withEqualProvider(Provider)} method. Checks
     * that we get back the {@link ProviderInstanceBindingVerifier}.
     */
    @Test
    public void testWithEqualProvider() {
        assertSame(verifier, verifier.withEqualProvider(new MyProvider(1)));

        verify(binding).getUserSuppliedProvider();
    }

    /**
     * Tests the {@link ProviderInstanceBindingVerifier#withEqualProvider(Provider)} method. Checks
     * that a {@link IncorrectBindingTargetException} is thrown when the
     * {@link ProviderInstanceBinding} is bound to a {@link Provider} not equal to the expected one.
     */
    @Test
    public void testWithEqualProvider_WrongProvider() {
        try {
            verifier.withEqualProvider(new MyProvider(2));
            fail("Should have thrown an IncorrectBindingTargetException.");
        } catch (final IncorrectBindingTargetException expected) {
            verify(binding).getUserSuppliedProvider();
        }
    }

    /**
     * Tests the {@link ProviderInstanceBindingVerifier#equals(Object)} and
     * {@link ProviderInstanceBindingVerifier#hashCode()} methods to check whether they conform to
     * their respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedProviderInstanceBindingVerifier<T> extends ProviderInstanceBindingVerifier<T> {

            ExtendedProviderInstanceBindingVerifier(final ProviderInstanceBinding<T> binding) {
                super(binding);
            }

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedProviderInstanceBindingVerifier;
            }
        }

        EqualsVerifier.forClass(ProviderInstanceBindingVerifier.class)
                .withRedefinedSubclass(ExtendedProviderInstanceBindingVerifier.class).verify();
    }

    /**
     * {@link Provider} implementation for use in unit testing.
     */
    private static class MyProvider implements Provider<Object> {

        private final int id;

        /**
         * @param id ID for the {@link Provider}
         */
        private MyProvider(final int id) {
            this.id = id;
        }

        @Override
        public Object get() {
            return new Object();
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            } else if (obj instanceof MyProvider) {
                final MyProvider rhs = (MyProvider) obj;
                return id == rhs.id;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
