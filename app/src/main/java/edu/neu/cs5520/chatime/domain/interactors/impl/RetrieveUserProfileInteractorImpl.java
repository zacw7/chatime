package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.RetrieveUserProfileInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;

public class RetrieveUserProfileInteractorImpl extends AbstractInteractor implements
        RetrieveUserProfileInteractor {

    private final Callback mCallback;
    private final UserRepository mUserRepository;

    public RetrieveUserProfileInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            UserRepository userRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserRepository = userRepository;
    }

    @Override
    public void run() {
        mUserRepository.getProfile(task -> {
            if (task.isSuccessful()) {
                mCallback.onRetrieveUserProfileSucceed(task.getResult());
            } else {
                mCallback.onRetrieveUserProfileFailed("Retrieving user profile failed");
            }
        });
    }
}
