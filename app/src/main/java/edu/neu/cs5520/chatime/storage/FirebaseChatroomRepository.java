package edu.neu.cs5520.chatime.storage;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.neu.cs5520.chatime.domain.repository.ChatroomRepository;

public class FirebaseChatroomRepository implements ChatroomRepository {

    private final String TAG = "ChatroomRepository";
    private final String COLLECTION_PATH = "rooms";
    private FirebaseFirestore mDb;
    private String mRoomId;

    public FirebaseChatroomRepository(String roomId) {
        this.mRoomId = roomId;
        this.mDb = FirebaseFirestore.getInstance();
    }

    @Override
    public void getRoomInfo(OnSuccessListener<DocumentSnapshot> listener) {
        mDb.collection(COLLECTION_PATH).document(mRoomId).get().addOnSuccessListener(listener);
    }
}
