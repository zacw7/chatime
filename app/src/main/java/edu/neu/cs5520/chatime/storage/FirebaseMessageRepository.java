package edu.neu.cs5520.chatime.storage;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.repository.MessageRepository;

public class FirebaseMessageRepository implements MessageRepository {

    private final String TAG = "FIRESTORE";
    private final String COLLECTION_PATH_FMT = "rooms/%s/messages";
    private CollectionReference mMessagesRef;
    private FirebaseFunctions mFunctions;
    private String mRoomId;

    public FirebaseMessageRepository(String roomId) {
        mRoomId = roomId;
        mFunctions = FirebaseFunctions.getInstance();
        mMessagesRef = FirebaseFirestore.getInstance().collection(String.format(COLLECTION_PATH_FMT, roomId));
    }

    @Override
    public void sendMessage(String to, String content) {
        Map<String, Object> data = new HashMap<>();
        data.put("roomId", mRoomId);
        data.put("to", to);
        data.put("content", content);

        mFunctions.getHttpsCallable("sendMessage").call(data);
    }

    @Override
    public void setMessageEventListener(EventListener<QuerySnapshot> listener) {
        mMessagesRef.addSnapshotListener(listener);
    }
}
