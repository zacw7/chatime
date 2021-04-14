package edu.neu.cs5520.chatime.domain.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.functions.HttpsCallableResult;

import edu.neu.cs5520.chatime.domain.model.DriftBottle;

public interface DriftBottleRepository {
    void createDriftBottle(DriftBottle driftBottle,
            OnCompleteListener<HttpsCallableResult> onCompleteListener);
}
