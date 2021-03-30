package edu.neu.cs5520.chatime.presentation.presenters.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.GetRoomInfoInteractor;
import edu.neu.cs5520.chatime.domain.interactors.ReceiveMessageInteractor;
import edu.neu.cs5520.chatime.domain.interactors.SendMessageInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.GetRoomInfoInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.ReceiveMessageInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.SendMessageInteractorImpl;
import edu.neu.cs5520.chatime.domain.model.Message;
import edu.neu.cs5520.chatime.domain.model.Room;
import edu.neu.cs5520.chatime.domain.model.User;
import edu.neu.cs5520.chatime.domain.repository.ChatroomRepository;
import edu.neu.cs5520.chatime.domain.repository.MessageRepository;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ChatPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.adapters.ChatMessageAdapter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.MessageViewModel;

public class ChatPresenterImpl extends AbstractPresenter implements ChatPresenter,
        GetRoomInfoInteractor.Callback, ReceiveMessageInteractor.Callback,
        SendMessageInteractor.Callback {

    private final String TAG = "ChatPresenter";
    private View mView;
    private ChatMessageAdapter mAdapter;
    private MessageRepository mMessageRepository;
    private ChatroomRepository mChatroomRepository;
    private UserRepository mUserRepository;
    private List<MessageViewModel> mItemList;
    private String mMeUid;
    private String mToUid;
    private Map<String, User> mUidMap;
    private GetRoomInfoInteractor mGetRoomInfoInteractor;
    private ReceiveMessageInteractor mReceiveMessageInteractor;

    public ChatPresenterImpl(Executor executor, MainThread mainThread,
            View view, MessageRepository messageRepository,
            ChatroomRepository chatroomRepository, UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mMessageRepository = messageRepository;
        mChatroomRepository = chatroomRepository;
        mUserRepository = userRepository;
        mItemList = new ArrayList<>();
        mUidMap = new HashMap<>();
        mGetRoomInfoInteractor = new GetRoomInfoInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mChatroomRepository
        );
        mReceiveMessageInteractor = new ReceiveMessageInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mMessageRepository
        );
    }

    @Override
    public void sendMessage(String content) {
        if (content == null || content.isEmpty()) {
            return;
        }
        SendMessageInteractor interactor = new SendMessageInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mMessageRepository,
                mToUid,
                content
        );
        interactor.execute();
    }

    @Override
    public void onSendMessageSucceed(String message) {

    }

    @Override
    public void onSendMessageFailed(String error) {

    }

    @Override
    public void resume() {
        // set my uid
        mMeUid = mUserRepository.getCurrentUser().getUid();

        // room info retrieval
        mView.showProgress();
        mGetRoomInfoInteractor.execute();

        // setup adapter
        mAdapter = new ChatMessageAdapter(mMeUid, mItemList);
        mView.setupAdapter(mAdapter);
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
    public void onRoomInfoRetrieved(Room room) {
        for (User user : room.getMembers()) {
            mUidMap.put(user.getUid(), user);
            if (!user.getUid().equals(mMeUid)) {
                mToUid = user.getUid();
            }
        }
        mView.setRecipient(mUidMap.get(mToUid).getUsername());
        mView.setChatTopic(room.getTopic());
        mView.enableChat();
        mReceiveMessageInteractor.execute();
    }

    @Override
    public void onMessagesReceived(List<Message> messageList) {
        mItemList.clear();
        for (Message message : messageList) {
            MessageViewModel item = new MessageViewModel(message, mUidMap);
            MessageViewModel last = mItemList.isEmpty() ? null : mItemList.get(mItemList.size() - 1);
            item.setDateShowing(last == null || !item.getDate().equals(last.getDate()));
            mItemList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }
}
