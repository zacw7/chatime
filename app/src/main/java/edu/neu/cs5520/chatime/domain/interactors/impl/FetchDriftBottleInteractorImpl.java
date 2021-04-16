package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.FetchDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;

public class FetchDriftBottleInteractorImpl extends AbstractInteractor implements
        FetchDriftBottleInteractor {

    private final Callback mCallback;
    private final DriftBottleRepository mDriftBottleRepository;

    public FetchDriftBottleInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            DriftBottleRepository driftBottleRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mDriftBottleRepository = driftBottleRepository;
    }

    @Override
    public void run() {
        mDriftBottleRepository.fetchDriftBottle(task -> {
            if (task.isSuccessful()) {
                mCallback.onFetchDriftBottleSucceed(task.getResult());
            } else {
                mCallback.onFetchDriftBottleFailed("Retrieving user profile failed");
            }
        });
    }
}
