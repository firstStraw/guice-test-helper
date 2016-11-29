package com.github.firststraw.guice;

import com.google.inject.Binding;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests the {@link BindingVerifier} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class BindingVerifierTest {

    @Mock
    private Binding<Integer> binding;

    /**
     * Invoked by JUnit after each test.
     */
    @After
    public void tearDown() {
        verifyNoMoreInteractions(binding);
    }

    /**
     * Tests the {@link BindingVerifier#withScoping()} method. Checks that we get back a
     * {@link BindingScopingVerifier} configured with the correct {@link Binding}.
     */
    @Test
    public void testWithScoping() {
        when(binding.acceptScopingVisitor(any(ScopingRecorder.class)))
                .thenReturn(Scoping.eagerSingleton());

        class MyBindingVerifier implements BindingVerifier<Integer> {

            @Override
            public Binding<Integer> getBinding() {
                return binding;
            }
        }

        final MyBindingVerifier verifier = new MyBindingVerifier();

        final BindingScopingVerifier scopingVerifier = verifier.withScoping();
        scopingVerifier.eagerSingleton();

        verify(binding).acceptScopingVisitor(any(ScopingRecorder.class));
    }
}
