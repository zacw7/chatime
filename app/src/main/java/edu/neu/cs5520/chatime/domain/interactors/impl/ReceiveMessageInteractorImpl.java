package edu.neu.cs5520.chatime.domain.interactors.impl;


import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.ReceiveMessageInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.model.Message;
import edu.neu.cs5520.chatime.domain.repository.MessageRepository;

public class ReceiveMessageInteractorImpl extends AbstractInteractor implements
        ReceiveMessageInteractor {

    private static final String TAG = "REC_MSG_INTERACTOR";
    private Callback mCallback;
    private MessageRepository mMessageRepository;

    public ReceiveMessageInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            MessageRepository messageRepository) {
        super(threadExecutor, mainThread);

        mCallback = callback;
        mMessageRepository = messageRepository;
    }

    @Override
    public void run() {
        mMessageRepository.setMessageEventListener((value, error) -> {
            if (error != null) {
                Log.w("TAG", "listen:error", error);
                return;
            }

            List<Message> messageList = new ArrayList<>();
            for (DocumentSnapshot doc : value.getDocuments()) {
                messageList.add(doc.toObject(Message.class));
            }
            Collections.sort(messageList,
                    (o1, o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
            mMainThread.post(() -> mCallback.onMessagesReceived(messageList));
        });
    }
}
