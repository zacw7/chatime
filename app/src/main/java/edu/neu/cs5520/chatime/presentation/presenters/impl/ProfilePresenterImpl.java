package edu.neu.cs5520.chatime.presentation.presenters.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UpdateUserDisplayNameInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.GetRoomInfoInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.UpdateUserDisplayNameInteractorImpl;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ProfilePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class ProfilePresenterImpl extends AbstractPresenter implements ProfilePresenter,
        UpdateUserDisplayNameInteractor.Callback {

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
    public void onUserDisplayNameUpdateSucceed(String message) {
        mView.showError(message);
        mView.hideProgress();
        mView.resetElements();
    }

    @Override
    public void onUserDisplayNameUpdateFailed(String message) {
        mView.showError(message);
        mView.hideProgress();
        mView.resetElements();
        reloadProfile();
    }

    @Override
    public void editProfile(String username) {
        UpdateUserDisplayNameInteractor interactor = new UpdateUserDisplayNameInteractorImpl(mExecutor,
                mMainThread, this, mUserRepository, username);
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
        mView.loadUserProfile(mUserRepository.getCurrentUser());
    }
}
