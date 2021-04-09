package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UpdateUserProfilePhotoInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;

public class UpdateUserProfilePhotoInteractorImpl extends AbstractInteractor implements
        UpdateUserProfilePhotoInteractor {

    private Callback mCallback;
    private UserRepository mUserRepository;
    private String mPhotoUrl;

    public UpdateUserProfilePhotoInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            UserRepository userRepository, String photoUrl) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserRepository = userRepository;
        mPhotoUrl = photoUrl;
    }

    @Override
    public void run() {
        mUserRepository.updatePhotoUrl(mPhotoUrl, task -> {
            if (task.isSuccessful()) {
                mCallback.onUserProfilePhotoUpdateSucceed("Update succeed");
            } else {
                mCallback.onUserProfilePhotoUpdateFailed("Update failed");
            }
        });
    }
}
