package edu.neu.cs5520.chatime.storage;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.repository.TopicRepository;

public class FirebaseTopicRepository implements TopicRepository {

    private final String TAG = "FirebaseTopicRepository";
    private final String COLLECTION_PATH = "topics";
    private final SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private FirebaseFirestore mDb;
    private FirebaseFunctions mFunctions;

    public FirebaseTopicRepository() {
        mDb = FirebaseFirestore.getInstance();
        mFunctions = FirebaseFunctions.getInstance();
    }

    @Override
    public void subscribeTopic(String userId, String topic) {
        Map<String, Object> data = new HashMap<>();
        data.put("topic", topic);

        mFunctions.getHttpsCallable("subscribeTopic").call(data);
    }

    @Override
    public void unsubscribeTopic(String userId, String topic) {
        DocumentReference topicRef = mDb.collection(COLLECTION_PATH).document(topic);
        topicRef.update("candidates", FieldValue.arrayRemove(userId));
    }

    private void addToTopic(String userId, String topic) {
        DocumentReference topicRef = mDb.collection(COLLECTION_PATH).document(topic);
        topicRef.update("candidates", FieldValue.arrayUnion(userId));
    }
}
