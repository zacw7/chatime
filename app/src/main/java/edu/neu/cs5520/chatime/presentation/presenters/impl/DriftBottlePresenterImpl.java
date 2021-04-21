package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.net.Uri;

import java.io.File;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.DownloadAudioInteractor;
import edu.neu.cs5520.chatime.domain.interactors.MessageBottleCreatorInteractor;
import edu.neu.cs5520.chatime.domain.interactors.PickDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.ThrowBackDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.DownloadAudioInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.MessageBottleCreatorInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.PickDriftBottleInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.ThrowBackDriftBottleInteractorImpl;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;
import edu.neu.cs5520.chatime.presentation.presenters.DriftBottlePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;

public class DriftBottlePresenterImpl extends AbstractPresenter implements DriftBottlePresenter,
        DownloadAudioInteractor.Callback, PickDriftBottleInteractor.Callback,
        ThrowBackDriftBottleInteractor.Callback, MessageBottleCreatorInteractor.Callback {

    private final String TAG = "EditProfilePhotoPresenter";
    private View mView;
    private DriftBottleRepository mDriftBottleRepository;
    private StorageRepository mStorageRepository;
    private DriftBottleViewModel mDriftBottle;
    private Uri audioUri;

    public DriftBottlePresenterImpl(Executor executor, MainThread mainThread, View view,
            StorageRepository storageRepository,
            DriftBottleRepository driftBottleRepository, DriftBottleViewModel driftBottle) {
        super(executor, mainThread);
        mView = view;
        mDriftBottleRepository = driftBottleRepository;
        mStorageRepository = storageRepository;
        mDriftBottle = driftBottle;
    }

    @Override
    public void onDownloadAudioSucceed(String message) {
        mView.hideProgress();
        mView.loadAudioPlayer(audioUri);
    }

    @Override
    public void onDownloadAudioFailed(String error) {
        mView.hideProgress();
        mView.showMessage(error);
    }

    @Override
    public void onPickDriftBottleSucceed(DriftBottle driftBottle) {
        mView.hideProgress();
        mView.displayBottle(new DriftBottleViewModel(driftBottle));
    }

    @Override
    public void onPickDriftBottleFailed(String error) {
        mView.hideProgress();
        mView.showMessage(error);
    }

    @Override
    public void onThrowBackDriftBottleSucceed(String message) {
        mView.hideProgress();
        mView.showMessage("The bottle has been thrown back to ocean");
        mView.finish();
    }

    @Override
    public void onMessageBottleCreatorSucceed(String roomId) {
        mView.hideProgress();
        if (roomId != null) {
            mView.enterChatRoom(roomId);
        }
    }

    @Override
    public void onMessageBottleCreatorFailed(String error) {
        mView.hideProgress();
        mView.showMessage(error);
    }

    @Override
    public void onThrowBackDriftBottleFailed(String error) {
        mView.hideProgress();
        mView.showMessage(error);
    }

    @Override
    public void pickDriftBottle() {
        PickDriftBottleInteractor interactor = new PickDriftBottleInteractorImpl(mExecutor,
                mMainThread, this, mDriftBottleRepository);
        mView.showProgress();
        interactor.execute();
    }

    @Override
    public void throwBackDriftBottle() {
        ThrowBackDriftBottleInteractor interactor = new ThrowBackDriftBottleInteractorImpl(
                mExecutor,
                mMainThread, this, mDriftBottleRepository, mDriftBottle.getId());
        mView.showProgress();
        interactor.execute();
    }

    @Override
    public void messageToCreator() {
        if (mDriftBottle.getRoomId() == null) {
            MessageBottleCreatorInteractor interactor = new MessageBottleCreatorInteractorImpl(
                    mExecutor,
                    mMainThread, this, mDriftBottleRepository, mDriftBottle.getId());
            mView.showProgress();
            interactor.execute();
        } else {
            mView.enterChatRoom(mDriftBottle.getRoomId());
        }
    }

    @Override
    public void resume() {
        mView.loadContentAndInfo(mDriftBottle.getContent(), mDriftBottle.getCreatorUsername(),
                mDriftBottle.getCreatedAt());
        if (mDriftBottle.getAudioUrl() != null) {
            String url = mDriftBottle.getAudioUrl();
            File file = mView.getCacheFile(url.substring(url.lastIndexOf("/") + 1));
            audioUri = Uri.fromFile(file);
            if (file.exists()) {
                mView.loadAudioPlayer(audioUri);
            } else {
                DownloadAudioInteractor interactor = new DownloadAudioInteractorImpl(mExecutor,
                        mMainThread, this, mStorageRepository, url, file);
                mView.showProgress();
                interactor.execute();
            }
        }
        if (mDriftBottle.getPhotoUrl() != null) {
            mView.loadPhoto(mDriftBottle.getPhotoUrl());
        }
        if (mDriftBottle.getLatitude() != null && mDriftBottle.getLongitude() != null) {
            mView.loadLocation(mDriftBottle.getLatitude(), mDriftBottle.getLongitude());
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
}
