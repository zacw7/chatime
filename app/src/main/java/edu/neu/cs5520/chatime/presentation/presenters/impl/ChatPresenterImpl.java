package edu.neu.cs5520.chatime.presentation.presenters.impl;

import java.util.ArrayList;
import java.util.List;

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
    private String mMeUsername;
    private String mToUid;
    private String mToUsername;
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
        // set my info
        User user = mUserRepository.getCurrentUser();
        mMeUid = user.getUid();
        mMeUsername = user.getUsername();

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
    public void onRoomInfoRetrieveSucceed(Room room) {
        mToUid = room.getRecipientUid();
        mToUsername = room.getRecipientUsername();
        mView.setRecipient(mToUsername);
        mView.setChatTopic(room.getTopic());
        mView.enableChat();
        mReceiveMessageInteractor.execute();
    }

    @Override
    public void onRoomInfoRetrieveFailed(String error) {
        mView.showError(error);
    }

    @Override
    public void onMessagesReceived(List<Message> messageList) {
        mItemList.clear();
        for (Message message : messageList) {
            MessageViewModel item = new MessageViewModel(message);
            item.setFromUid(message.getFrom());
            item.setToUid(message.getTo());
            if (mToUid.equals(message.getFrom())) {
                item.setToUid(mToUsername);
                item.setFromUsername(mMeUsername);
            } else {
                item.setToUid(mMeUsername);
                item.setFromUsername(mToUsername);
            }
            MessageViewModel last = mItemList.isEmpty() ? null : mItemList.get(
                    mItemList.size() - 1);
            item.setDateShowing(last == null || !item.getDate().equals(last.getDate()));
            mItemList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }
}
