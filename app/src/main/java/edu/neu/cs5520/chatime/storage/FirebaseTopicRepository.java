package edu.neu.cs5520.chatime.storage;

import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.repository.TopicRepository;

public class FirebaseTopicRepository implements TopicRepository {

    private final String TAG = "FirebaseTopicRepository";
    private FirebaseFunctions mFunctions;

    public FirebaseTopicRepository() {
        mFunctions = FirebaseFunctions.getInstance();
    }

    @Override
    public void subscribeTopic(String topic) {
        Map<String, Object> data = new HashMap<>();
        data.put("topic", topic);

        mFunctions.getHttpsCallable("subscribeTopic").call(data);
    }

    @Override
    public void unsubscribe() {
        mFunctions.getHttpsCallable("unsubscribeTopic").call();
    }
}
