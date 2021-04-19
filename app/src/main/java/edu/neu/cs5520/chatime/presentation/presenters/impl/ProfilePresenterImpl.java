package edu.neu.cs5520.chatime.presentation.presenters.impl;

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
        mView.loadUserProfile(new ProfileViewModel(user));
    }

    @Override
    public void onRetrieveUserProfileFailed(String error) {
        mView.showError(error);
    }

    @Override
    public void onUserProfileUpdateSucceed(String message) {
        mView.showError(message);
        mView.hideProgress();
        mView.resetElements();
    }

    @Override
    public void onUserProfileUpdateFailed(String message) {
        mView.showError(message);
        mView.hideProgress();
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
        mView.showError(error);
    }

    @Override
    public void editProfile(String username, String about) {
        UpdateUserProfileInteractor interactor = new UpdateUserProfileInteractorImpl(mExecutor,
                mMainThread, this, mUserRepository, username, about);
        interactor.execute();
        mView.showProgress();
    }

    @Override
    public void dailyCheckIn() {
        DailyCheckInInteractor interactor = new DailyCheckInInteractorImpl(mExecutor,
                mMainThread, this, mUserRepository);
        interactor.execute();
        mView.showProgress();
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
        mView.showError(message);
    }

    private void reloadProfile() {
        RetrieveUserProfileInteractor interactor = new RetrieveUserProfileInteractorImpl(mExecutor,
                mMainThread, this, mUserRepository);
        interactor.execute();
    }
}
