package com.github.firststraw.guice;

import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import java.lang.annotation.Annotation;
import nl.jqno.equalsverifier.EqualsVerifier;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the {@link ScopingRecorder} class.
 */
public class ScopingRecorderTest {

    private final ScopingRecorder recorder = new ScopingRecorder();

    /**
     * Tests the {@link ScopingRecorder#visitEagerSingleton()} method. Checks that we get back a
     * {@link Scoping} configured for eager singleton scoping.
     */
    @Test
    public void testVisitEagerSingleton() {
        assertEquals(Scoping.eagerSingleton(), recorder.visitEagerSingleton());
    }

    /**
     * Tests the {@link ScopingRecorder#visitScope(Scope)} method. Checks that a
     * {@link NullPointerException} is thrown when the {@link Scope} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testVisitScope_NullScope() {
        recorder.visitScope(null);
    }

    /**
     * Tests the {@link ScopingRecorder#visitScope(Scope)} method. Checks that we get back a
     * {@link Scoping} configured for a specific {@link Scope}.
     */
    @Test
    public void testVisitScope() {
        final Scope scope = Scopes.SINGLETON;
        assertEquals(Scoping.scope(scope), recorder.visitScope(scope));
    }

    /**
     * Tests the {@link ScopingRecorder#visitScopeAnnotation(Class)} method. Checks that a
     * {@link NullPointerException} is thrown when the scope annotation is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testVisitScopeAnnotation_NullScopeAnnotation() {
        recorder.visitScopeAnnotation(null);
    }

    /**
     * Tests the {@link ScopingRecorder#visitScopeAnnotation(Class)} method. Checks that we get back
     * a {@link Scoping} configured for a specific scope annotation.
     */
    @Test
    public void testVisitScopeAnnotation() {
        final Class<? extends Annotation> scopeAnnotation = Singleton.class;
        assertEquals(Scoping.scopeAnnotation(scopeAnnotation),
                recorder.visitScopeAnnotation(scopeAnnotation));
    }

    /**
     * Tests the {@link ScopingRecorder#visitNoScoping()} method. Checks that we get back a
     * {@link Scoping} configured for the lack of any scoping.
     */
    @Test
    public void testVisitNoScoping() {
        assertEquals(Scoping.noScoping(), recorder.visitNoScoping());
    }

    /**
     * Tests the {@link ScopingRecorder#equals(Object)} and {@link ScopingRecorder#hashCode()}
     * methods to check whether they conform to their respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedScopingRecorder extends ScopingRecorder {

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedScopingRecorder;
            }
        }

        EqualsVerifier.forClass(ScopingRecorder.class)
                .withRedefinedSubclass(ExtendedScopingRecorder.class).verify();
    }
}
