package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.DailyCheckInInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;

public class DailyCheckInInteractorImpl extends AbstractInteractor implements
        DailyCheckInInteractor {

    private static final String FOLDER = "audios";
    private Callback mCallback;
    private UserRepository mUserRepository;

    public DailyCheckInInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            UserRepository userRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserRepository = userRepository;
    }

    @Override
    public void run() {
        mUserRepository.dailyCheckIn(task -> {
            if (task.isSuccessful()) {
                mCallback.onDailyCheckInSucceed(task.getResult().toString());
            } else {
                task.getException().printStackTrace();
                mCallback.onDailyCheckInFailed(task.getException().getMessage());
            }
        });
    }
}
