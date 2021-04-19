package edu.neu.cs5520.chatime.domain.interactors.impl;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.GetBottleListInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;

public class GetBottleListInteractorImpl extends AbstractInteractor implements
        GetBottleListInteractor {

    private Callback mCallback;
    private DriftBottleRepository mDriftBottleRepository;
    private String mUid;

    public GetBottleListInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            DriftBottleRepository driftBottleRepository,
            String uid) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mDriftBottleRepository = driftBottleRepository;
        mUid = uid;
    }

    @Override
    public void run() {
        mDriftBottleRepository.getBottleList(mUid, task -> {
            if (task.isSuccessful()) {
                List<DriftBottle> list = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    DriftBottle bottle = document.toObject(DriftBottle.class);
                    bottle.setId(document.getId());
                    list.add(bottle);
                }
                mCallback.onBottleListRetrieveSucceed(list);
            } else {
                mCallback.onBottleListRetrievedFailed(task.getException().getMessage());
            }
        });
    }
}
