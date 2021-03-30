package edu.neu.cs5520.chatime.domain.interactors.impl;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.GetRoomListInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.model.Room;
import edu.neu.cs5520.chatime.domain.model.User;
import edu.neu.cs5520.chatime.domain.repository.ChatroomRepository;

public class GetRoomListInteractorImpl extends AbstractInteractor implements GetRoomListInteractor {

    private GetRoomListInteractor.Callback mCallback;
    private ChatroomRepository mChatroomRepository;
    private String mUid;

    public GetRoomListInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            GetRoomListInteractor.Callback callback,
            ChatroomRepository chatroomRepository,
            String uid) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mChatroomRepository = chatroomRepository;
        mUid = uid;
    }

    @Override
    public void run() {
        mChatroomRepository.getRoomList(querySnapshots -> {
            List<Room> roomList = new ArrayList();
            for (DocumentSnapshot document : querySnapshots.getDocuments()) {
                Room room = document.toObject(Room.class);
                room.setRoomId(document.getId());
                for (User member : room.getMembers()) {
                    if (mUid.equals(member.getUid())) {
                        roomList.add(room);
                    }
                }
            }
            mMainThread.post(() -> mCallback.onRoomListRetrieved(roomList));
        });
    }
}
