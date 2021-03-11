package edu.neu.cs5520.chatime.domain.interactors;

import org.junit.Test;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.impl.WelcomingInteractorImpl;
import edu.neu.cs5520.chatime.domain.repository.MessageRepository;
import edu.neu.cs5520.chatime.threading.TestMainThread;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


/**
 * Tests our welcoming interactor.
 */
public class GetWelcomeMessageTest {

    private MainThread mainThread;
    @Mock
    private Executor executor;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private WelcomingInteractor.Callback mMockedCallback;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mainThread = new TestMainThread();
    }

    @Test
    public void testWelcomeMessageNotFound() throws Exception {
        WelcomingInteractorImpl interactor = new WelcomingInteractorImpl(executor, mainThread,
                mMockedCallback, messageRepository);
        interactor.run();

        Mockito.when(messageRepository.getWelcomeMessage())
                .thenReturn(null);

        Mockito.verify(messageRepository).getWelcomeMessage();
        Mockito.verifyNoMoreInteractions(messageRepository);
        Mockito.verify(mMockedCallback).onRetrievalFailed(anyString());
    }

    @Test
    public void testWelcomeMessageFound() throws Exception {

        String msg = "Welcome, friend!";

        when(messageRepository.getWelcomeMessage())
                .thenReturn(msg);

        WelcomingInteractorImpl interactor = new WelcomingInteractorImpl(executor, mainThread,
                mMockedCallback, messageRepository);
        interactor.run();

        Mockito.verify(messageRepository).getWelcomeMessage();
        Mockito.verifyNoMoreInteractions(messageRepository);
        Mockito.verify(mMockedCallback).onMessageRetrieved(msg);
    }
}