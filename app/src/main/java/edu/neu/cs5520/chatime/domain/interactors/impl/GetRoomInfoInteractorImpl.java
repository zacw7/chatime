package edu.neu.cs5520.chatime.domain.interactors.impl;


import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.GetRoomInfoInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.ChatroomRepository;

public class GetRoomInfoInteractorImpl extends AbstractInteractor implements GetRoomInfoInteractor {

    private Callback mCallback;
    private ChatroomRepository mChatroomRepository;

    public GetRoomInfoInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            ChatroomRepository chatroomRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mChatroomRepository = chatroomRepository;
    }

    @Override
    public void run() {
        mChatroomRepository.getRoomInfo(task -> {
            if (task.isSuccessful()) {
                mCallback.onRoomInfoRetrieveSucceed(task.getResult());
            } else {
                mCallback.onRoomInfoRetrieveFailed(task.getException().getMessage());
            }
        });
    }
}
