package edu.neu.cs5520.chatime.domain.interactors.impl;


import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.PickDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;

public class PickDriftBottleInteractorImpl extends AbstractInteractor implements
        PickDriftBottleInteractor {

    private Callback mCallback;
    private DriftBottleRepository mDriftBottleRepository;

    public PickDriftBottleInteractorImpl(Executor threadExecutor,
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
                mCallback.onPickDriftBottleSucceed(task.getResult());
            } else {
                mCallback.onPickDriftBottleFailed(task.getException().getMessage());
            }
        });
    }
}
