package edu.neu.cs5520.chatime.storage;

import com.google.firebase.firestore.FirebaseFirestore;

import edu.neu.cs5520.chatime.domain.model.Chatroom;
import edu.neu.cs5520.chatime.domain.repository.ChatroomRepository;

public class FirebaseChatroomRepository implements ChatroomRepository {

    private final String TAG = "ChatroomRepository";
    private final String COLLECTION_PATH = "chatrooms";
    private FirebaseFirestore mDb;

    public FirebaseChatroomRepository() {
        this.mDb = FirebaseFirestore.getInstance();
    }

    @Override
    public void createChatroom(Chatroom chatroom) {
        mDb.collection(COLLECTION_PATH).document(chatroom.getRoomId()).set(chatroom);
    }
}
