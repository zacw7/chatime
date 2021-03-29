package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.SendMessageInteractor;
import edu.neu.cs5520.chatime.domain.model.Chatroom;
import edu.neu.cs5520.chatime.domain.model.Message;
import edu.neu.cs5520.chatime.domain.model.User;
import edu.neu.cs5520.chatime.domain.repository.MessageRepository;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ChatPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.adapters.ChatMessageAdapter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.MessageViewModel;

public class ChatPresenterImpl extends AbstractPresenter implements ChatPresenter,
        SendMessageInteractor.Callback,
        EventListener<DocumentSnapshot> {

    private final String TAG = "ChatPresenter";
    private View mView;
    private ChatMessageAdapter mAdapter;
    private MessageRepository mMessageRepository;
    private UserRepository mUserRepository;
    private List<MessageViewModel> mItemList;
    private String mChatoomId;
    private String mRecipient;

    public ChatPresenterImpl(Executor executor, MainThread mainThread,
            View view, String chatoomId, MessageRepository messageRepository,
            UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mChatoomId = chatoomId;
        mMessageRepository = messageRepository;
        mUserRepository = userRepository;
        mItemList = new ArrayList<>();
    }

    @Override
    public void sendMessage(String content) {
        if (content == null || content.isEmpty()) {
            return;
        }
        Message message = new Message(mUserRepository.getCurrentUser().getUid(), mRecipient,
                content, System.currentTimeMillis());
        mMessageRepository.sendMessage(mChatoomId, message);
    }

    @Override
    public void onSendMessageSucceed(String message) {

    }

    @Override
    public void onSendMessageFailed(String error) {

    }

    @Override
    public void resume() {
        // setup adapter
        mAdapter = new ChatMessageAdapter(mUserRepository.getCurrentUser().getUid(), mItemList);
        mView.setupAdapter(mAdapter);
        // setup data change listener
        mMessageRepository.setEventListener(mChatoomId, this);
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        mView.showError(message);
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot value,
            @Nullable FirebaseFirestoreException error) {
        if (error != null) {
            Log.w(TAG, "Listen failed.", error);
            return;
        }

        if (value != null && value.exists()) {
            Chatroom chatroom = value.toObject(Chatroom.class);
            mView.setChatTopic(chatroom.getTopic());
            Map<String, User> userMap = new HashMap<>();
            for (User user : chatroom.getMembers()) {
                if (mRecipient == null && !user.getUid().equals(
                        mUserRepository.getCurrentUser().getUid())) {
                    mRecipient = user.getUid();
                    mView.setRecipient(user.getUsername());
                }
                userMap.put(user.getUid(), user);
            }
            mItemList.clear();
            for (Message message : chatroom.getMessages()) {
                MessageViewModel item = new MessageViewModel(message, userMap);
                MessageViewModel last = mItemList.isEmpty() ? null : mItemList.get(
                        mItemList.size() - 1);
                item.setDateShowing(last == null || !item.getDate().equals(last.getDate()));
                mItemList.add(item);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            Log.d(TAG, "Current data: null");
        }
    }
}
