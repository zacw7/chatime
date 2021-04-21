package edu.neu.cs5520.chatime.domain.interactors.impl;


import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.MessageBottleCreatorInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;

public class MessageBottleCreatorInteractorImpl extends AbstractInteractor implements
        MessageBottleCreatorInteractor {

    private Callback mCallback;
    private DriftBottleRepository mDriftBottleRepository;
    private String mBottleId;

    public MessageBottleCreatorInteractorImpl(Executor threadExecutor,
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
        mDriftBottleRepository.messageToCreator(mBottleId, task -> {
            if (task.isSuccessful()) {
                mCallback.onMessageBottleCreatorSucceed(task.getResult());
            } else {
                mCallback.onMessageBottleCreatorFailed(task.getException().getMessage());
            }
        });
    }
}
