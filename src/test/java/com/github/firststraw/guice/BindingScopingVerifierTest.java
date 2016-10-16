package com.github.firststraw.guice;

import com.google.inject.Binding;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import java.lang.annotation.Annotation;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests the {@link BindingScopingVerifier} class.
 */
public class BindingScopingVerifierTest {

    private final Binding<?> binding = mock(Binding.class);
    private final BindingScopingVerifier verifier = new BindingScopingVerifier(binding);

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(binding);
    }

    /**
     * Tests the {@link BindingScopingVerifier#BindingScopingVerifier(Binding)} constructor. Checks
     * that a {@link NullPointerException} is thrown when the {@link Binding} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_NullBinding() {
        new BindingScopingVerifier(null);
    }

    /**
     * Tests the {@link BindingScopingVerifier#eagerSingleton()} method. Checks that an
     * {@link IncorrectScopingException} is not thrown when the {@link Binding} has eager singleton
     * scoping.
     */
    @Test
    public void testEagerSingleton() {
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.eagerSingleton());

        verifier.eagerSingleton();

        verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
    }

    /**
     * Tests the {@link BindingScopingVerifier#eagerSingleton()} method. Checks that an
     * {@link IncorrectScopingException} is thrown when the {@link Binding} does not have eager
     * singleton scoping.
     */
    @Test
    public void testEagerSingleton_WrongScoping() {
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.noScoping());

        try {
            verifier.eagerSingleton();
            fail("Should have thrown an IncorrectScopingException.");
        } catch (final IncorrectScopingException expected) {
            verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
        }
    }

    /**
     * Tests the {@link BindingScopingVerifier#scope(Scope)} method. Checks that a
     * {@link NullPointerException} is thrown when the {@link Scope} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testScope_NullScope() {
        verifier.scope(null);
    }

    /**
     * Tests the {@link BindingScopingVerifier#scope(Scope)} method. Checks that an
     * {@link IncorrectScopingException} is not thrown when the {@link Binding} is configured with
     * the correct {@link Scope}.
     */
    @Test
    public void testScope() {
        final Scope scope = Scopes.SINGLETON;
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.scope(scope));

        verifier.scope(scope);

        verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
    }

    /**
     * Tests the {@link BindingScopingVerifier#scope(Scope)} method. Checks that an
     * {@link IncorrectScopingException} is thrown when the {@link Binding} is not configured with
     * the correct {@link Scope}.
     */
    @Test
    public void testScope_WrongScoping() {
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.noScoping());

        try {
            verifier.scope(Scopes.SINGLETON);
            fail("Should have thrown an IncorrectScopingException.");
        } catch (final IncorrectScopingException expected) {
            verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
        }
    }

    /**
     * Tests the {@link BindingScopingVerifier#scopeAnnotation(Class)} method. Checks that a
     * {@link NullPointerException} is thrown when the scope annotation is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testScope_NullScopeAnnotation() {
        verifier.scopeAnnotation(null);
    }

    /**
     * Tests the {@link BindingScopingVerifier#scopeAnnotation(Class)} method. Checks that an
     * {@link IncorrectScopingException} is not thrown when the {@link Binding} is configured with
     * the correct scope annotation.
     */
    @Test
    public void testScopeAnnotation() {
        final Class<? extends Annotation> scopeAnnotation = Singleton.class;
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.scopeAnnotation(scopeAnnotation));

        verifier.scopeAnnotation(scopeAnnotation);

        verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
    }

    /**
     * Tests the {@link BindingScopingVerifier#scopeAnnotation(Class)} method. Checks that an
     * {@link IncorrectScopingException} is thrown when the {@link Binding} is not configured with
     * the correct scope annotation.
     */
    @Test
    public void testScopeAnnotation_WrongScoping() {
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.noScoping());

        try {
            verifier.scopeAnnotation(Singleton.class);
            fail("Should have thrown an IncorrectScopingException.");
        } catch (final IncorrectScopingException expected) {
            verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
        }
    }

    /**
     * Tests the {@link BindingScopingVerifier#noScoping()} method. Checks that an
     * {@link IncorrectScopingException} is not thrown when the {@link Binding} has no scoping.
     */
    @Test
    public void testNoScoping() {
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.noScoping());

        verifier.noScoping();

        verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
    }

    /**
     * Tests the {@link BindingScopingVerifier#noScoping()} method. Checks that an
     * {@link IncorrectScopingException} is thrown when the {@link Binding} has scoping.
     */
    @Test
    public void testNoScoping_WrongScoping() {
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.eagerSingleton());

        try {
            verifier.noScoping();
            fail("Should have thrown an IncorrectScopingException.");
        } catch (final IncorrectScopingException expected) {
            verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
        }
    }

    /**
     * Tests the {@link BindingScopingVerifier#equals(Object)} and
     * {@link BindingScopingVerifier#hashCode()} methods to check whether they conform to their
     * respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedBindingScopingVerifier extends BindingScopingVerifier {

            ExtendedBindingScopingVerifier(final Binding<?> binding) {
                super(binding);
            }

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedBindingScopingVerifier;
            }
        }

        EqualsVerifier.forClass(BindingScopingVerifier.class)
                .withRedefinedSubclass(ExtendedBindingScopingVerifier.class).verify();
    }
}
