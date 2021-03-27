package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.CreateDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;

public class CreateDriftBottleInteractorImpl extends AbstractInteractor implements
        CreateDriftBottleInteractor {

    private Callback mCallback;
    private DriftBottleRepository mDriftBottleRepository;
    private DriftBottle mDriftBottle;

    public CreateDriftBottleInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            DriftBottleRepository driftBottleRepository,
            DriftBottle driftBottle) {
        super(threadExecutor, mainThread);
        this.mCallback = callback;
        this.mDriftBottleRepository = driftBottleRepository;
        this.mDriftBottle = driftBottle;
    }

    @Override
    public void run() {
        try {
            mDriftBottleRepository.createDriftBottle(mDriftBottle);
            mMainThread.post(() -> mCallback.onCreatingRandomBottleSucceed("Created"));
        } catch (Exception e) {
            mMainThread.post(() -> mCallback.onCreatingRandomBottleFailed("Creating failed"));
        }
    }
}
