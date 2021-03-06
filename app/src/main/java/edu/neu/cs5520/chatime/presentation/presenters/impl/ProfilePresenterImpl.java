package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.DailyCheckInInteractor;
import edu.neu.cs5520.chatime.domain.interactors.RetrieveUserProfileInteractor;
import edu.neu.cs5520.chatime.domain.interactors.UpdateUserProfileInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.DailyCheckInInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.RetrieveUserProfileInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.UpdateUserProfileInteractorImpl;
import edu.neu.cs5520.chatime.domain.model.User;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ProfilePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.ProfileViewModel;

public class ProfilePresenterImpl extends AbstractPresenter implements ProfilePresenter,
        RetrieveUserProfileInteractor.Callback, UpdateUserProfileInteractor.Callback,
        DailyCheckInInteractor.Callback {

    private final String TAG = "ProfilePresenter";
    private View mView;
    private UserRepository mUserRepository;

    public ProfilePresenterImpl(Executor executor, MainThread mainThread, View view,
            UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void onRetrieveUserProfileSucceed(User user) {
        mView.hideProgress();
        mView.loadUserProfile(new ProfileViewModel(user));
    }

    @Override
    public void onRetrieveUserProfileFailed(String error) {
        mView.hideProgress();
        mView.showMessage(error);
    }

    @Override
    public void onUserProfileUpdateSucceed(String message) {
        mView.hideProgress();
        mView.showMessage(message);
        mView.resetElements();
    }

    @Override
    public void onUserProfileUpdateFailed(String message) {
        mView.hideProgress();
        mView.showMessage(message);
        mView.resetElements();
        reloadProfile();
    }

    @Override
    public void onDailyCheckInSucceed(String message) {
        mView.hideProgress();
        reloadProfile();
    }

    @Override
    public void onDailyCheckInFailed(String error) {
        mView.hideProgress();
        mView.showMessage(error);
    }

    @Override
    public void editProfile(String username, String about) {
        mView.showProgress();
        UpdateUserProfileInteractor interactor = new UpdateUserProfileInteractorImpl(mExecutor,
                mMainThread, this, mUserRepository, username, about);
        interactor.execute();
    }

    @Override
    public void dailyCheckIn() {
        mView.showProgress();
        DailyCheckInInteractor interactor = new DailyCheckInInteractorImpl(mExecutor,
                mMainThread, this, mUserRepository);
        interactor.execute();
    }

    @Override
    public void onSignOut() {
        unsubscribeTo(mUserRepository.getCurrentUser().getUid());
    }

    @Override
    public void resume() {
        reloadProfile();
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

    private void reloadProfile() {
        mView.showProgress();
        RetrieveUserProfileInteractor interactor = new RetrieveUserProfileInteractorImpl(mExecutor,
                mMainThread, this, mUserRepository);
        interactor.execute();
    }

    private void unsubscribeTo(String uid) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(uid)
                .addOnCompleteListener(task -> {
                    String msg = "Unsubscribed: " + uid;
                    if (!task.isSuccessful()) {
                        msg = "Unsubscribe failed";
                    }
                    Log.d(TAG, msg);
                });
    }
}
