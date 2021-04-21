package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.GetRoomListInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.ChatroomRepository;

public class GetRoomListInteractorImpl extends AbstractInteractor implements GetRoomListInteractor {

    private GetRoomListInteractor.Callback mCallback;
    private ChatroomRepository mChatroomRepository;

    public GetRoomListInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            GetRoomListInteractor.Callback callback,
            ChatroomRepository chatroomRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mChatroomRepository = chatroomRepository;
    }

    @Override
    public void run() {
        mChatroomRepository.getRoomList(task -> {
            if (task.isSuccessful()) {
                mCallback.onRoomListRetrieveSucceed(task.getResult());
            } else {
                mCallback.onRoomListRetrieveFailed(task.getException().getMessage());
            }
        });
    }
}
