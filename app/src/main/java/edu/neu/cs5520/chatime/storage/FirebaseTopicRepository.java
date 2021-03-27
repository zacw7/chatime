package edu.neu.cs5520.chatime.storage;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.repository.TopicRepository;

public class FirebaseTopicRepository implements TopicRepository {

    private final String TAG = "FirebaseTopicRepository";
    private final SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private FirebaseFirestore db;

    public FirebaseTopicRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void subscribeTopic(String userId, String topic) {
        // Create Topic if not exists
        DocumentReference docRef = db.collection("topics").document(topic);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document == null || !document.exists()) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("candidates", new ArrayList<>());
                    db.collection("topics").document(topic).set(data,
                            SetOptions.merge()).addOnSuccessListener(
                            aVoid -> addToTopic(userId, topic));
                } else {
                    addToTopic(userId, topic);
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void unsubscribeTopic(String userId, String topic) {
        DocumentReference topicRef = db.collection("topics").document(topic);
        topicRef.update("candidates", FieldValue.arrayRemove(userId));
    }

    private void addToTopic(String userId, String topic) {
        DocumentReference topicRef = db.collection("topics").document(topic);
        topicRef.update("candidates", FieldValue.arrayUnion(userId));
    }
}
