package com.github.firststraw.guice;

import com.google.inject.spi.ConstructorBinding;
import com.google.inject.spi.InjectionPoint;
import java.lang.reflect.Constructor;
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
 * Tests the {@link ConstructorBindingVerifier} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructorBindingVerifierTest {

    private static final Constructor<Integer> CONSTRUCTOR_WITH_INT;
    private static final Constructor<Integer> CONSTRUCTOR_WITH_STR;

    @Mock
    private ConstructorBinding<Integer> binding;

    private ConstructorBindingVerifier<Integer> verifier;

    static {
        try {
            CONSTRUCTOR_WITH_INT = Integer.class.getDeclaredConstructor(int.class);
            CONSTRUCTOR_WITH_STR = Integer.class.getDeclaredConstructor(String.class);
        } catch (final NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException("Could not retrieve constructors.");
        }
    }

    /**
     * Invoked by JUnit prior to each test.
     */
    @Before
    public void setUp() {
        verifier = new ConstructorBindingVerifier<>(binding);

        when(binding.getConstructor())
                .thenReturn(InjectionPoint.forConstructor(CONSTRUCTOR_WITH_INT));
    }

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(binding);
    }

    /**
     * Tests the {@link ConstructorBindingVerifier#ConstructorBindingVerifier(ConstructorBinding)}
     * constructor. Checks that a {@link NullPointerException} is thrown when the
     * {@link ConstructorBinding} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_NullBinding() {
        new ConstructorBindingVerifier<>(null);
    }

    /**
     * Tests the {@link ConstructorBindingVerifier#withAnyConstructor()} method. Checks that we get
     * back the {@link ConstructorBindingVerifier}.
     */
    @Test
    public void testWithAnyConstructor() {
        assertSame(verifier, verifier.withAnyConstructor());
    }

    /**
     * Tests the {@link ConstructorBindingVerifier#withConstructor(Constructor)} method. Checks that
     * a {@link NullPointerException} is thrown when the {@link Constructor} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithConstructor_NullConstructor() {
        verifier.withConstructor(null);
    }

    /**
     * Tests the {@link ConstructorBindingVerifier#withConstructor(Constructor)} method. Checks that
     * we get back the {@link ConstructorBindingVerifier} when the {@link Constructor} matches the
     * expected one.
     */
    @Test
    public void testWithConstructor() {
        assertSame(verifier, verifier.withConstructor(CONSTRUCTOR_WITH_INT));

        verify(binding).getConstructor();
    }

    /**
     * Tests the {@link ConstructorBindingVerifier#withConstructor(Constructor)} method. Checks that
     * an {@link IncorrectBindingTargetException} is thrown when the {@link Constructor} does not
     * match the expected one.
     */
    @Test
    public void testWithConstructor_WrongConstructor() {
        try {
            assertSame(verifier, verifier.withConstructor(CONSTRUCTOR_WITH_STR));
            fail("Should have thrown an IncorrectBindingTargetException.");
        } catch (final IncorrectBindingTargetException ex) {
            verify(binding).getConstructor();
        }
    }

    /**
     * Tests the {@link ConstructorBindingVerifier#equals(Object)} and
     * {@link ConstructorBindingVerifier#hashCode()} methods to check whether they conform to their
     * respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedConstructorBindingVerifier<T> extends ConstructorBindingVerifier<T> {

            ExtendedConstructorBindingVerifier(final ConstructorBinding<T> binding) {
                super(binding);
            }

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedConstructorBindingVerifier;
            }
        }

        EqualsVerifier.forClass(ConstructorBindingVerifier.class)
                .withRedefinedSubclass(ExtendedConstructorBindingVerifier.class).verify();
    }
}
