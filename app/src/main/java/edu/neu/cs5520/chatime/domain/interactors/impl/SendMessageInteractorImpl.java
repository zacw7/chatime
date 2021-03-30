package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.SendMessageInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.MessageRepository;

public class SendMessageInteractorImpl extends AbstractInteractor implements SendMessageInteractor {

    private Callback mCallback;
    private MessageRepository mMessageRepository;
    private String mTo;
    private String mContent;

    public SendMessageInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            MessageRepository messageRepository,
            String to, String content) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mMessageRepository = messageRepository;
        mTo = to;
        mContent = content;
    }

    @Override
    public void run() {
        try {
            mMessageRepository.sendMessage(mTo, mContent);
            mMainThread.post(() -> mCallback.onSendMessageSucceed("Succeed!"));
        } catch (Exception e) {
            mMainThread.post(() -> mCallback.onSendMessageFailed("Submit failed"));
        }
    }
}
