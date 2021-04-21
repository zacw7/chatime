package edu.neu.cs5520.chatime.presentation.presenters.impl;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.GetRoomListInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.GetRoomListInteractorImpl;
import edu.neu.cs5520.chatime.domain.model.Room;
import edu.neu.cs5520.chatime.domain.repository.ChatroomRepository;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ChatListPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.adapters.ChatListAdapter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.ChatViewModel;

public class ChatListPresenterImpl extends AbstractPresenter implements ChatListPresenter,
        GetRoomListInteractor.Callback, ChatListAdapter.OnItemClickListener {

    private final String TAG = "ChatListPresenter";
    private View mView;
    private ChatListAdapter mAdapter;
    private ChatroomRepository mChatroomRepository;
    private UserRepository mUserRepository;
    private List<ChatViewModel> mItemList;
    private String mMeUid;

    public ChatListPresenterImpl(Executor executor, MainThread mainThread, View view,
            ChatroomRepository chatroomRepository, UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mChatroomRepository = chatroomRepository;
        mUserRepository = userRepository;
        mMeUid = mUserRepository.getCurrentUser().getUid();
        mItemList = new ArrayList<>();
    }

    @Override
    public void resume() {
        // setup adapter
        mAdapter = new ChatListAdapter(mItemList, this);
        mView.setupAdapter(mAdapter);

        GetRoomListInteractor interactor = new GetRoomListInteractorImpl(mExecutor,
                mMainThread,
                this,
                mChatroomRepository);
        interactor.execute();
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
        mView.showMessage(message);
    }

    @Override
    public void onRoomListRetrieveSucceed(List<Room> roomList) {
        mItemList.clear();
        for (Room room : roomList) {
            ChatViewModel model = new ChatViewModel();
            model.setRoomId(room.getId());
            model.setTopic(room.getTopic());
            model.setUsername(room.getRecipientUsername());
            model.setLastTime(room.getLastMessageTime());
            mItemList.add(model);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRoomListRetrieveFailed(String error) {
        mView.showMessage(error);
    }

    @Override
    public void onItemClick(ChatViewModel item) {
        mView.enterChatRoom(item.getRoomId());
    }
}
