package edu.neu.cs5520.chatime.domain.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.HttpsCallableResult;

import edu.neu.cs5520.chatime.domain.model.DriftBottle;

public interface DriftBottleRepository {
    void createDriftBottle(DriftBottle driftBottle,
            OnCompleteListener<HttpsCallableResult> onCompleteListener);

    void fetchDriftBottle(OnCompleteListener<DriftBottle> onCompleteListener);

    void getBottleList(String uid, OnCompleteListener<QuerySnapshot> onCompleteListener);

    void throwBackDriftBottle(String bottleId,
            OnCompleteListener<HttpsCallableResult> onCompleteListener);

    void messageToCreator(String bottleId, OnCompleteListener<String> onCompleteListener);
}
