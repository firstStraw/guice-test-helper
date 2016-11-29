package com.github.firststraw.guice;

import com.google.inject.Binding;
import com.google.inject.spi.ConstructorBinding;
import com.google.inject.spi.InstanceBinding;
import com.google.inject.spi.LinkedKeyBinding;
import com.google.inject.spi.ProviderInstanceBinding;
import com.google.inject.spi.ProviderKeyBinding;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.After;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests the {@link BindingTypeVerifier} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class BindingTypeVerifierTest {

    @Mock
    private ConstructorBinding<Integer> constructorBinding;

    @Mock
    private InstanceBinding<Integer> instanceBinding;

    @Mock
    private LinkedKeyBinding<Integer> linkedKeyBinding;

    @Mock
    private ProviderInstanceBinding<Integer> providerInstanceBinding;

    @Mock
    private ProviderKeyBinding<Integer> providerKeyBinding;

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(constructorBinding);
        verifyNoMoreInteractions(instanceBinding);
        verifyNoMoreInteractions(linkedKeyBinding);
        verifyNoMoreInteractions(providerInstanceBinding);
        verifyNoMoreInteractions(providerKeyBinding);
    }

    /**
     * Tests the {@link BindingTypeVerifier#BindingTypeVerifier(Binding)} constructor. Checks that a
     * {@link NullPointerException} is thrown when the {@link Binding} is {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructor_NullBinding() {
        new BindingTypeVerifier<>(null);
    }

    /**
     * Tests the {@link BindingTypeVerifier#asConstructorBinding()} method. Checks that we get back
     * a {@link ConstructorBindingVerifier} built with the correct {@link ConstructorBinding}.
     */
    @Test
    public void testAsConstructorBinding() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(constructorBinding);

        final ConstructorBindingVerifier<Integer> bindingVerifier = verifier.asConstructorBinding();
        assertSame(constructorBinding, bindingVerifier.getBinding());
    }

    /**
     * Tests the {@link BindingTypeVerifier#asConstructorBinding()} method. Checks that an
     * {@link IncorrectBindingTypeException} is thrown when the {@link Binding} is not a
     * {@link ConstructorBinding}.
     */
    @Test(expected = IncorrectBindingTypeException.class)
    public void testAsConstructorBinding_WrongBindingType() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(instanceBinding);
        verifier.asConstructorBinding();
    }

    /**
     * Tests the {@link BindingTypeVerifier#asInstanceBinding()} method. Checks that we get back an
     * {@link InstanceBindingVerifier} built with the correct {@link InstanceBinding}.
     */
    @Test
    public void testAsInstanceBinding() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(instanceBinding);

        final InstanceBindingVerifier<Integer> bindingVerifier = verifier.asInstanceBinding();
        assertSame(instanceBinding, bindingVerifier.getBinding());
    }

    /**
     * Tests the {@link BindingTypeVerifier#asInstanceBinding()} method. Checks that an
     * {@link IncorrectBindingTypeException} is thrown when the {@link Binding} is not an
     * {@link InstanceBinding}.
     */
    @Test(expected = IncorrectBindingTypeException.class)
    public void testAsInstanceBinding_WrongBindingType() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(constructorBinding);
        verifier.asInstanceBinding();
    }

    /**
     * Tests the {@link BindingTypeVerifier#asLinkedKeyBinding()} method. Checks that we get back a
     * {@link LinkedKeyBindingVerifier} built with the correct {@link LinkedKeyBinding}.
     */
    @Test
    public void testAsLinkedKeyBinding() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(linkedKeyBinding);

        final LinkedKeyBindingVerifier<Integer> bindingVerifier = verifier.asLinkedKeyBinding();
        assertSame(linkedKeyBinding, bindingVerifier.getBinding());
    }

    /**
     * Tests the {@link BindingTypeVerifier#asLinkedKeyBinding()} method. Checks that an
     * {@link IncorrectBindingTypeException} is thrown when the {@link Binding} is not a
     * {@link LinkedKeyBinding}.
     */
    @Test(expected = IncorrectBindingTypeException.class)
    public void testAsLinkedKeyBinding_WrongBindingType() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(instanceBinding);
        verifier.asLinkedKeyBinding();
    }

    /**
     * Tests the {@link BindingTypeVerifier#asProviderInstanceBinding()} method. Checks that we get
     * back a {@link ProviderInstanceBindingVerifier} built with the correct
     * {@link ProviderInstanceBinding}.
     */
    @Test
    public void testAsProviderInstanceBinding() {
        final BindingTypeVerifier<Integer> verifier =
                new BindingTypeVerifier<>(providerInstanceBinding);

        final ProviderInstanceBindingVerifier<Integer> bindingVerifier =
                verifier.asProviderInstanceBinding();
        assertSame(providerInstanceBinding, bindingVerifier.getBinding());
    }

    /**
     * Tests the {@link BindingTypeVerifier#asProviderInstanceBinding()} method. Checks that an
     * {@link IncorrectBindingTypeException} is thrown when the {@link Binding} is not a
     * {@link ProviderInstanceBinding}.
     */
    @Test(expected = IncorrectBindingTypeException.class)
    public void testAsProviderInstanceBinding_WrongBindingType() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(instanceBinding);
        verifier.asProviderInstanceBinding();
    }

    /**
     * Tests the {@link BindingTypeVerifier#asProviderKeyBinding()} method. Checks that we get back
     * a {@link ProviderKeyBindingVerifier} built with the correct {@link ProviderKeyBinding}.
     */
    @Test
    public void testAsProviderKeyBinding() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(providerKeyBinding);

        final ProviderKeyBindingVerifier<Integer> bindingVerifier = verifier.asProviderKeyBinding();
        assertSame(providerKeyBinding, bindingVerifier.getBinding());
    }

    /**
     * Tests the {@link BindingTypeVerifier#asProviderKeyBinding()} method. Checks that an
     * {@link IncorrectBindingTypeException} is thrown when the {@link Binding} is not a
     * {@link ProviderKeyBinding}.
     */
    @Test(expected = IncorrectBindingTypeException.class)
    public void testAsProviderKeyBinding_WrongBindingType() {
        final BindingTypeVerifier<Integer> verifier = new BindingTypeVerifier<>(instanceBinding);
        verifier.asProviderKeyBinding();
    }

    /**
     * Tests the {@link BindingTypeVerifier#equals(Object)} and
     * {@link BindingTypeVerifier#hashCode()} methods to check whether they conform to their
     * respective contracts.
     */
    @Test
    public void testEqualsContract() {
        class ExtendedBindingTypeVerifier<T> extends BindingTypeVerifier<T> {

            ExtendedBindingTypeVerifier(final Binding<T> binding) {
                super(binding);
            }

            @Override
            protected boolean canEqual(final Object obj) {
                return obj instanceof ExtendedBindingTypeVerifier;
            }
        }

        EqualsVerifier.forClass(BindingTypeVerifier.class)
                .withRedefinedSubclass(ExtendedBindingTypeVerifier.class).verify();
    }
}
