package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.net.Uri;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.DownloadAudioInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.DownloadAudioInteractorImpl;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;
import edu.neu.cs5520.chatime.presentation.presenters.DriftBottlePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;

public class DriftBottlePresenterImpl extends AbstractPresenter implements DriftBottlePresenter,
        DownloadAudioInteractor.Callback {

    private final String TAG = "EditProfilePhotoPresenter";
    private View mView;
    private StorageRepository mStorageRepository;
    private DriftBottleViewModel mDriftBottle;
    private Uri audioUri;

    public DriftBottlePresenterImpl(Executor executor, MainThread mainThread, View view,
            StorageRepository storageRepository,
            DriftBottleRepository driftBottleRepository, DriftBottleViewModel driftBottle) {
        super(executor, mainThread);
        mView = view;
        mStorageRepository = storageRepository;
        mDriftBottle = driftBottle;
    }

    @Override
    public void onDownloadAudioSucceed(String message) {
        mView.loadAudioPlayer(audioUri);
        mView.hideProgress();
    }

    @Override
    public void onDownloadAudioFailed(String error) {
        mView.showError(error);
        mView.hideProgress();
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
                interactor.execute();
            }
        }
        if (mDriftBottle.getPhotoUrl() != null) {
            mView.loadPhoto(mDriftBottle.getPhotoUrl());
        }
        if (mDriftBottle.getLatitude() != null && mDriftBottle.getLongitude() != null) {
            mView.loadLocation(mDriftBottle.getLatitude(), mDriftBottle.getLongitude());
        }
        mView.showProgress();
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