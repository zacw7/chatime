package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.SendMessageInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.model.Message;
import edu.neu.cs5520.chatime.domain.repository.MessageRepository;

public class SendMessageInteractorImpl extends AbstractInteractor implements SendMessageInteractor {

    private Callback mCallback;
    private MessageRepository mMessageRepository;
    private String mChatroomId;
    private Message mMessage;

    public SendMessageInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            MessageRepository messageRepository,
            String chatroomId,
            Message message) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mMessageRepository = messageRepository;
        mChatroomId = chatroomId;
        mMessage = message;
    }

    @Override
    public void run() {
        try {
            mMessageRepository.sendMessage(mChatroomId, mMessage);
            mMainThread.post(() -> mCallback.onSendMessageSucceed("Succeed!"));
        } catch (Exception e) {
            mMainThread.post(() -> mCallback.onSendMessageFailed("Submit failed"));
        }
    }
}
