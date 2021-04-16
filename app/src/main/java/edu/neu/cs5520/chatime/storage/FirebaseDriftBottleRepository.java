package edu.neu.cs5520.chatime.storage;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;

public class FirebaseDriftBottleRepository implements DriftBottleRepository {

    private final String TAG = "DriftBottleRepository";
    private FirebaseFunctions mFunctions;

    public FirebaseDriftBottleRepository() {
        mFunctions = FirebaseFunctions.getInstance();
    }

    @Override
    public void createDriftBottle(DriftBottle driftBottle,
            OnCompleteListener<HttpsCallableResult> onCompleteListener) {
        Map<String, Object> data = new HashMap<>();
        data.put("content", driftBottle.getContent());
        if (driftBottle.getAudioUrl() != null) {
            data.put("audioUrl", driftBottle.getAudioUrl());
        }
        if (driftBottle.getPhotoUrl() != null) {
            data.put("photoUrl", driftBottle.getPhotoUrl());
        }
        if (driftBottle.getLatitude() != null && driftBottle.getLongitude() != null) {
            data.put("latitude", driftBottle.getLatitude());
            data.put("longitude", driftBottle.getLongitude());
        }

        mFunctions.getHttpsCallable("createDriftBottle").call(data).addOnCompleteListener(
                onCompleteListener);
    }
}
