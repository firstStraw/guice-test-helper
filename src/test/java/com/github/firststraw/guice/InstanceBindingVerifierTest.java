package com.github.firststraw.guice;

import com.google.inject.spi.InstanceBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
 * Tests the {@link InstanceBindingVerifier} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class InstanceBindingVerifierTest {

    @Mock
    private InstanceBinding<List<Object>> binding;

    private InstanceBindingVerifier<List<Object>> verifier;

    /**
     * Invoked by JUnit prior to each test.
     */
    @Before
    public void setUp() {
        verifier = new InstanceBindingVerifier<>(binding);
    }

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(binding);
    }

    /**
     * Tests the {@link InstanceBindingVerifier#InstanceBindingVerifier(InstanceBinding)}
     * constructor. Checks that a {@link NullPointerException} is thrown when the
     * {@link InstanceBinding} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_NullBinding() {
        new InstanceBindingVerifier<>(null);
    }

    /**
     * Tests the {@link InstanceBindingVerifier#withInstance(Object)} method. Checks that a
     * {@link NullPointerException} is thrown when the expected instance is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithInstance_NullExpectedInstance() {
        verifier.withInstance(null);
    }

    /**
     * Tests the {@link InstanceBindingVerifier#withInstance(Object)}. Checks that we get back the
     * {@link InstanceBindingVerifier}.
     */
    @Test
    public void testWithInstance() {
        when(binding.getInstance()).thenReturn(Collections.emptyList());

        assertSame(verifier, verifier.withInstance(Collections.emptyList()));

        verify(binding).getInstance();
    }

    /**
     * Tests the {@link InstanceBindingVerifier#withInstance(Object)}. Checks that an
     * {@link IncorrectBindingTargetException} is thrown when the {@link InstanceBinding} is bound
     * to an instance other than the expected instance.
     */
    @Test
    public void testWithInstance_WrongInstance() {
        when(binding.getInstance()).thenReturn(Collections.emptyList());

        try {
            verifier.withInstance(new ArrayList<>(1));
            fail("Should have thrown an IncorrectBindingTargetException.");
        } catch (final IncorrectBindingTargetException expected) {
            verify(binding).getInstance();
        }
    }

    /**
     * Tests the {@link InstanceBindingVerifier#withEqualInstance(Object)} method. Checks that a
     * {@link NullPointerException} is thrown when the expected instance is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testWithEqualInstance_NullExpectedInstance() {
        verifier.withEqualInstance(null);
    }

    /**
     * Tests the {@link InstanceBindingVerifier#withEqualInstance(Object)}. Checks that we get back
     * the {@link InstanceBindingVerifier}.
     */
    @Test
    public void testWithEqualInstance() {
        when(binding.getInstance()).thenReturn(Collections.emptyList());

        assertSame(verifier, verifier.withEqualInstance(new ArrayList<>(1)));

        verify(binding).getInstance();
    }

    /**
     * Tests the {@link InstanceBindingVerifier#withEqualInstance(Object)}. Checks that an
     * {@link IncorrectBindingTargetException} is thrown when the {@link InstanceBinding} is bound
     * to an instance not equal to the expected instance.
     */
    @Test
    public void testWithEqualInstance_UnequalInstance() {
        when(binding.getInstance()).thenReturn(Collections.emptyList());

        try {
            verifier.withEqualInstance(Collections.singletonList(new Object()));
            fail("Should have thrown an IncorrectBindingTargetException.");
        } catch (final IncorrectBindingTargetException expected) {
            verify(binding).getInstance();
        }
    }

    /**
     * Tests the {@link InstanceBindingVerifier#equals(Object)} and
     * {@link InstanceBindingVerifier#hashCode()} methods to check whether they conform to their
     * respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedInstanceBindingVerifier<T> extends InstanceBindingVerifier<T> {

            ExtendedInstanceBindingVerifier(final InstanceBinding<T> binding) {
                super(binding);
            }

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedInstanceBindingVerifier;
            }
        }

        EqualsVerifier.forClass(InstanceBindingVerifier.class)
                .withRedefinedSubclass(ExtendedInstanceBindingVerifier.class).verify();
    }
}
