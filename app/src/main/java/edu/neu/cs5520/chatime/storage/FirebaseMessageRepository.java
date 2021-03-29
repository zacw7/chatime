package edu.neu.cs5520.chatime.storage;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.neu.cs5520.chatime.domain.model.Message;
import edu.neu.cs5520.chatime.domain.repository.MessageRepository;

public class FirebaseMessageRepository implements MessageRepository {

    private final String TAG = "FIRESTORE";
    private final String COLLECTION_PATH = "chatrooms";
    private FirebaseFirestore mDb;

    public FirebaseMessageRepository() {
        mDb = FirebaseFirestore.getInstance();
    }

    @Override
    public void sendMessage(String chatroomId,  Message message) {
        DocumentReference topicRef = mDb.collection(COLLECTION_PATH).document(chatroomId);
        topicRef.update("messages", FieldValue.arrayUnion(message));
    }

    @Override
    public void setEventListener(String chatroomId, EventListener<DocumentSnapshot> listener) {
        final DocumentReference docRef = mDb.collection("chatrooms").document(chatroomId);
        docRef.addSnapshotListener(listener);
    }
}
