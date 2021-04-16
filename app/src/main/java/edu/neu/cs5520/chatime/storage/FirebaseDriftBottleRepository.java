package edu.neu.cs5520.chatime.storage;

import com.google.android.gms.tasks.OnCompleteListener;
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
        if (driftBottle.getId() != null) {
            data.put("id", driftBottle.getId());
        }
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

    @Override
    public void fetchDriftBottle(OnCompleteListener<DriftBottle> onCompleteListener) {
        mFunctions.getHttpsCallable("fetchDriftBottle").call().continueWith(
                task -> {
                    Map<String, Object> map = (Map) task.getResult().getData();
                    DriftBottle driftBottle = new DriftBottle();
                    driftBottle.setId((String) map.get("id"));
                    driftBottle.setCreatorUid((String) map.get("creatorUid"));
                    driftBottle.setCreatorUsername((String) map.get("creatorUsername"));
                    driftBottle.setContent((String) map.get("content"));
                    if (map.containsKey("photoUrl")) {
                        driftBottle.setPhotoUrl((String) map.get("photoUrl"));
                    }
                    if (map.containsKey("audioUrl")) {
                        driftBottle.setAudioUrl((String) map.get("audioUrl"));
                    }
                    if (map.containsKey("latitude") && map.containsKey("longitude")) {
                        driftBottle.setLatitude((double) map.get("latitude"));
                        driftBottle.setLongitude((double) map.get("longitude"));
                    }
                    return driftBottle;
                }).addOnCompleteListener(onCompleteListener);
    }
}
