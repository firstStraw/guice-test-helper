package com.github.firststraw.guice;

import static com.github.firststraw.guice.Scoping.EAGER_SINGLETON;
import static com.github.firststraw.guice.Scoping.NO_SCOPING;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import java.lang.annotation.Annotation;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests the {@link Scoping} class.
 */
public class ScopingTest {

    /**
     * Tests the {@link Scoping#eagerSingleton()} method. Checks that the {@link Scoping} is built
     * correctly.
     */
    @Test
    public void testEagerSingleton() {
        final Scoping scoping = Scoping.eagerSingleton();
        assertTrue(scoping.isEagerSingleton());
        assertNull(scoping.getScope());
        assertNull(scoping.getScopeAnnotation());
        assertFalse(scoping.isNoScoping());
        assertSame(EAGER_SINGLETON, scoping.getDescription());
    }

    /**
     * Tests the {@link Scoping#scope(Scope)} method. Checks that a {@link NullPointerException} is
     * thrown when the {@link Scope} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testScope_NullScope() {
        Scoping.scope(null);
    }

    /**
     * Tests the {@link Scoping#scope(Scope)} method. Checks that the {@link Scoping} is built
     * correctly.
     */
    @Test
    public void testScope() {
        final Scope scope = Scopes.SINGLETON;

        final Scoping scoping = Scoping.scope(scope);
        assertFalse(scoping.isEagerSingleton());
        assertSame(scope, scoping.getScope());
        assertNull(scoping.getScopeAnnotation());
        assertFalse(scoping.isNoScoping());
        assertEquals(scope.toString(), scoping.getDescription());
    }

    /**
     * Tests the {@link Scoping#scopeAnnotation(Class)} method. Checks that a
     * {@link NullPointerException} is thrown when the scope annotation is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testScopeAnnotation_NullScopeAnnotation() {
        Scoping.scopeAnnotation(null);
    }

    /**
     * Tests the {@link Scoping#scopeAnnotation(Class)} method. Checks that the {@link Scoping} is
     * built correctly.
     */
    @Test
    public void testScopeAnnotation() {
        final Class<? extends Annotation> scopeAnnotation = Singleton.class;

        final Scoping scoping = Scoping.scopeAnnotation(scopeAnnotation);
        assertFalse(scoping.isEagerSingleton());
        assertNull(scoping.getScope());
        assertSame(scopeAnnotation, scoping.getScopeAnnotation());
        assertFalse(scoping.isNoScoping());
        assertEquals(scopeAnnotation.toString(), scoping.getDescription());
    }

    /**
     * Tests the {@link Scoping#noScoping()} method. Checks that the {@link Scoping} is built
     * correctly.
     */
    @Test
    public void testNoScoping() {
        final Scoping scoping = Scoping.noScoping();
        assertFalse(scoping.isEagerSingleton());
        assertNull(scoping.getScope());
        assertNull(scoping.getScopeAnnotation());
        assertTrue(scoping.isNoScoping());
        assertEquals(NO_SCOPING, scoping.getDescription());
    }

    /**
     * Tests the {@link Scoping#equals(Object)} and {@link Scoping#hashCode()} methods to check
     * whether they conform to their respective contracts.
     */
    @Test
    public void testEqualsContract() {
        // The Scoping class only has a private constructor, so it cannot be extended.  Because of
        // this, we can safely ignore the strict inheritance rules imposed by EqualsVerifier.
        EqualsVerifier.forClass(Scoping.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }
}
