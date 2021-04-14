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
            DriftBottleRepository driftBottleRepository, DriftBottle driftBottle) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mDriftBottleRepository = driftBottleRepository;
        mDriftBottle = driftBottle;
    }

    @Override
    public void run() {
        mDriftBottleRepository.createDriftBottle(mDriftBottle,
                task -> {
                    if (task.isSuccessful()) {
                        mCallback.onCreateDriftBottleSucceed("succeed");
                    } else {
                        mCallback.onCreateDriftBottleFailed(task.getException().getMessage());
                    }
                });
    }
}
