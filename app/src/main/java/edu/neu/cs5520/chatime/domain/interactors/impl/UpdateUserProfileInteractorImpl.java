package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UpdateUserProfileInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;

public class UpdateUserProfileInteractorImpl extends AbstractInteractor implements
        UpdateUserProfileInteractor {

    private Callback mCallback;
    private UserRepository mUserRepository;
    private String mDisplayName;
    private String mAbout;

    public UpdateUserProfileInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            UserRepository userRepository, String displayName, String about) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserRepository = userRepository;
        mDisplayName = displayName;
        mAbout = about;
    }

    @Override
    public void run() {
        mUserRepository.updateProfile(mDisplayName, mAbout, task -> {
            if (task.isSuccessful()) {
                mCallback.onUserProfileUpdateSucceed("Update succeed");
            } else {
                mCallback.onUserProfileUpdateFailed("Update failed");
            }
        });
    }
}
