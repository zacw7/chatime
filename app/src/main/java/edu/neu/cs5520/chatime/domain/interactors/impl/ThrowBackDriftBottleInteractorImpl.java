package edu.neu.cs5520.chatime.domain.interactors.impl;


import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.ThrowBackDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;

public class ThrowBackDriftBottleInteractorImpl extends AbstractInteractor implements
        ThrowBackDriftBottleInteractor {

    private Callback mCallback;
    private DriftBottleRepository mDriftBottleRepository;
    private String mBottleId;

    public ThrowBackDriftBottleInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            DriftBottleRepository driftBottleRepository,
            String bottleId) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mDriftBottleRepository = driftBottleRepository;
        mBottleId = bottleId;
    }

    @Override
    public void run() {
        mDriftBottleRepository.throwBackDriftBottle(mBottleId,
                task -> {
                    if (task.isSuccessful()) {
                        mCallback.onThrowBackDriftBottleSucceed("throw back succeed");
                    } else {
                        mCallback.onThrowBackDriftBottleFailed(task.getException().getMessage());
                    }
                });
    }
}
