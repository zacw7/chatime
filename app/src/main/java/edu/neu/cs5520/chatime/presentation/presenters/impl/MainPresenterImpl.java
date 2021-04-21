package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.MainPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter {

    private final static String TAG = "MAIN_PRESENTER";
    private MainPresenter.View mView;
    private UserRepository mUserRepository;

    public MainPresenterImpl(Executor executor, MainThread mainThread,
            View view, UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void onSigned() {
        subscribeTo(mUserRepository.getCurrentUser().getUid());
    }

    @Override
    public void resume() {
        if (mUserRepository.getCurrentUser() == null) {
            mView.showSignIn();
        } else {
            onSigned();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        mView.showMessage(message);
    }

    private void subscribeTo(String uid) {
        FirebaseMessaging.getInstance().subscribeToTopic(uid)
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed: " + uid;
                    if (!task.isSuccessful()) {
                        msg = "Subscribe failed";
                    }
                    Log.d(TAG, msg);
                });
    }
}
