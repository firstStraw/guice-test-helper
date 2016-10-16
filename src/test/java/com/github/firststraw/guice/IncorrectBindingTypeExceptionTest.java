package com.github.firststraw.guice;

import com.google.inject.Binding;
import com.google.inject.spi.ConstructorBinding;
import com.google.inject.spi.InstanceBinding;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests the {@link IncorrectBindingTypeException} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class IncorrectBindingTypeExceptionTest {

    @Mock
    private InstanceBinding<Object> instanceBinding;

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(instanceBinding);
    }

    /**
     * Checks that the {@link IncorrectBindingTypeException} is configured correctly.
     */
    @Test
    public void test() {
        final Class<ConstructorBinding> expectedType = ConstructorBinding.class;
        final Binding<?> foundBinding = instanceBinding;
        final IncorrectBindingTypeException ex =
                new IncorrectBindingTypeException(expectedType, foundBinding);

        final String message = "Expected a binding of type " + expectedType
                + ", but found binding of type " + foundBinding.getClass();
        assertEquals(message, ex.getMessage());
    }
}
