package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UpdateUserDisplayNameInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;

public class UpdateUserDisplayNameInteractorImpl extends AbstractInteractor implements
        UpdateUserDisplayNameInteractor {

    private Callback mCallback;
    private UserRepository mUserRepository;
    private String mDisplayName;

    public UpdateUserDisplayNameInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            UserRepository userRepository, String displayName) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserRepository = userRepository;
        mDisplayName = displayName;
    }

    @Override
    public void run() {
        mUserRepository.updateUsername(mDisplayName, task -> {
            if (task.isSuccessful()) {
                mCallback.onUserDisplayNameUpdateSucceed("Update succeed");
            } else {
                mCallback.onUserDisplayNameUpdateFailed("Update failed");
            }
        });
    }
}
