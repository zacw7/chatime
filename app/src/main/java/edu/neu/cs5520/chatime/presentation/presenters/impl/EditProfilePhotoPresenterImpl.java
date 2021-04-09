package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.net.Uri;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UpdateUserProfilePhotoInteractor;
import edu.neu.cs5520.chatime.domain.interactors.UploadFileInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.UpdateUserProfilePhotoInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.UploadFileInteractorImpl;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.EditProfilePhotoPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class EditProfilePhotoPresenterImpl extends AbstractPresenter implements
        EditProfilePhotoPresenter,
        UploadFileInteractor.Callback,
        UpdateUserProfilePhotoInteractor.Callback {

    private final String TAG = "EditProfilePhotoPresenter";
    private final String FOLDER = "profiles";
    private View mView;
    private StorageRepository mStorageRepository;
    private UserRepository mUserRepository;
    private Uri mFile;

    public EditProfilePhotoPresenterImpl(Executor executor, MainThread mainThread, View view,
            StorageRepository storageRepository, UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mStorageRepository = storageRepository;
        mUserRepository = userRepository;
    }

    @Override
    public void selectFile(Uri uri) {
        mFile = uri;
        mView.displaySelectedPhoto(uri);
    }

    @Override
    public void onUploadButtonClick() {
        UploadFileInteractor
                interactor = new UploadFileInteractorImpl(mExecutor,
                mMainThread, this, mStorageRepository, mFile, FOLDER);
        interactor.execute();
        mView.showProgress();
    }

    @Override
    public void onUserProfilePhotoUpdateSucceed(String message) {
        mView.hideProgress();
        mView.closeItself();
    }

    @Override
    public void onUserProfilePhotoUpdateFailed(String message) {
        mView.hideProgress();
        mView.showError(message);
    }

    @Override
    public void onUploadFileSucceed(String location) {
        UpdateUserProfilePhotoInteractor interactor = new UpdateUserProfilePhotoInteractorImpl(
                mExecutor, mMainThread, this, mUserRepository, location);
        interactor.execute();
    }

    @Override
    public void onUploadFileFailed(String message) {
        mView.hideProgress();
        mView.showError(message);
    }

    @Override
    public void resume() {
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

}
