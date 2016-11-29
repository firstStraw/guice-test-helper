package com.github.firststraw.guice;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.LinkedKeyBinding;
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
 * Tests the {@link LinkedKeyBindingVerifier} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class LinkedKeyBindingVerifierTest {

    private static final Key<? extends Object> KEY = Key.get(String.class);

    @Mock
    private LinkedKeyBinding<Object> binding;

    private LinkedKeyBindingVerifier<Object> verifier;

    /**
     * Invoked by JUnit prior to each test.
     */
    @Before
    public void setUp() {
        verifier = new LinkedKeyBindingVerifier<>(binding);

        when(binding.getLinkedKey()).thenAnswer(invocation -> KEY);
    }

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(binding);
    }

    /**
     * Tests the {@link LinkedKeyBindingVerifier#LinkedKeyBindingVerifier(LinkedKeyBinding)}
     * constructor. Checks that a {@link NullPointerException} is thrown when the
     * {@link LinkedKeyBinding} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_NullBinding() {
        new LinkedKeyBindingVerifier<>(null);
    }

    /**
     * Tests the {@link LinkedKeyBindingVerifier#withClass(Class)} method. Checks that a
     * {@link NullPointerException} is thrown when the {@link Class} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithClass_NullClass() {
        verifier.withClass(null);
    }

    /**
     * Tests the {@link LinkedKeyBindingVerifier#withClass(Class)} method. Checks that we get back
     * the {@link LinkedKeyBindingVerifier}.
     */
    @Test
    public void testWithClass() {
        assertSame(verifier, verifier.withClass(String.class));

        verify(binding).getLinkedKey();
    }

    /**
     * Tests the {@link LinkedKeyBindingVerifier#withClass(Class)} method. Checks that an
     * {@link IncorrectBindingTargetException} is thrown when the {@link LinkedKeyBinding} is bound
     * to a {@link Class} other than the expected one.
     */
    @Test
    public void testWithClass_WrongClass() {
        try {
            verifier.withClass(Integer.class);
            fail("Should have thrown an IncorrectBindingTargetException.");
        } catch (final IncorrectBindingTargetException expected) {
            verify(binding).getLinkedKey();
        }
    }

    /**
     * Tests the {@link LinkedKeyBindingVerifier#withTypeLiteral(TypeLiteral)} method. Checks that a
     * {@link NullPointerException} is thrown when the {@link TypeLiteral} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithTypeLiteral_NullTypeLiteral() {
        verifier.withTypeLiteral(null);
    }

    /**
     * Tests the {@link LinkedKeyBindingVerifier#withTypeLiteral(TypeLiteral)} method. Checks that
     * we get back the {@link LinkedKeyBindingVerifier}.
     */
    @Test
    public void testWithTypeLiteral() {
        assertSame(verifier, verifier.withTypeLiteral(TypeLiteral.get(String.class)));

        verify(binding).getLinkedKey();
    }

    /**
     * Tests the {@link LinkedKeyBindingVerifier#withKey(Key)} method. Checks that a
     * {@link NullPointerException} is thrown when the {@link Key} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithKey_NullKey() {
        verifier.withKey(null);
    }

    /**
     * Tests the {@link LinkedKeyBindingVerifier#equals(Object)} and
     * {@link LinkedKeyBindingVerifier#hashCode()} methods to check whether they conform to their
     * respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedLinkedKeyBindingVerifier<T> extends LinkedKeyBindingVerifier<T> {

            ExtendedLinkedKeyBindingVerifier(final LinkedKeyBinding<T> binding) {
                super(binding);
            }

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedLinkedKeyBindingVerifier;
            }
        }

        EqualsVerifier.forClass(LinkedKeyBindingVerifier.class)
                .withRedefinedSubclass(ExtendedLinkedKeyBindingVerifier.class).verify();
    }
}
