package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.CreateDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.UploadAudioInteractor;
import edu.neu.cs5520.chatime.domain.interactors.UploadDriftBottlePhotoInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.CreateDriftBottleInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.UploadAudioInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.UploadDriftBottlePhotoInteractorImpl;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;
import edu.neu.cs5520.chatime.presentation.presenters.CreateBottlePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class CreateBottlePresenterImpl extends AbstractPresenter implements
        CreateBottlePresenter, UploadDriftBottlePhotoInteractor.Callback,
        UploadAudioInteractor.Callback, CreateDriftBottleInteractor.Callback {

    private final String TAG = "EditProfilePhotoPresenter";
    private View mView;
    private StorageRepository mStorageRepository;
    private DriftBottleRepository mDriftBottleRepository;
    private Uri mPhotoUri;
    private Uri mAudioUri;
    private LatLng mLocation;
    private DriftBottle mDriftBottle;
    private AtomicInteger mNumOfBackgroundTasks;

    public CreateBottlePresenterImpl(Executor executor, MainThread mainThread, View view,
            StorageRepository storageRepository, DriftBottleRepository driftBottleRepository) {
        super(executor, mainThread);
        mView = view;
        mStorageRepository = storageRepository;
        mDriftBottleRepository = driftBottleRepository;
    }

    @Override
    public void addPhoto(Uri uri) {
        mPhotoUri = uri;
        mView.displayAddedPhoto(uri);
    }

    @Override
    public void removePhoto() {
        mPhotoUri = null;
        mView.hidePhotoPreview();
    }

    @Override
    public void startAudioAdding() {
        if (mAudioUri == null) {
            mView.startAudioRecorder();
        }
    }

    @Override
    public void cancelAudioAdding() {
        mAudioUri = null;
        mView.closeAudioSection();
    }

    @Override
    public void addAudio(Uri uri) {
        mAudioUri = uri;
        mView.closeAudioRecorder();
        mView.startAudioPlayer();
    }

    @Override
    public void removeAudio() {
        mAudioUri = null;
        mView.closeAudioPlayer();
        mView.startAudioRecorder();
    }

    @Override
    public void startLocationAdding() {
        if (mLocation == null) {
            mView.startLocationPicker();
        }
    }

    @Override
    public void cancelLocationAdding() {
        mLocation = null;
        mView.clearLocation();
        mView.closeLocationPicker();
    }

    @Override
    public void addLocation(LatLng location) {
        mLocation = location;
        mView.saveLocation(String.format(Locale.US, "lat/lng: (%.2f, %.2f)", location.latitude,
                location.longitude));
    }

    @Override
    public void removeLocation() {
        mLocation = null;
        mView.clearLocation();
    }

    @Override
    public void createBottle(String content) {
        if (content == null || content.isEmpty()) {
            mView.showError("Content cannot be null");
            return;
        }
        mView.showProgress();
        mDriftBottle = new DriftBottle();
        mDriftBottle.setContent(content);

        mNumOfBackgroundTasks = new AtomicInteger(0);
        if (mPhotoUri != null) {
            mNumOfBackgroundTasks.incrementAndGet();
            UploadDriftBottlePhotoInteractor
                    interactor = new UploadDriftBottlePhotoInteractorImpl(mExecutor,
                    mMainThread, this, mStorageRepository, mPhotoUri);
            interactor.execute();
        }
        if (mAudioUri != null) {
            mNumOfBackgroundTasks.incrementAndGet();
            UploadAudioInteractor
                    interactor = new UploadAudioInteractorImpl(mExecutor,
                    mMainThread, this, mStorageRepository, mAudioUri);
            interactor.execute();
        }
        if (mLocation != null) {
            mDriftBottle.setLatitude(mLocation.latitude);
            mDriftBottle.setLongitude(mLocation.longitude);
        }

        if (mNumOfBackgroundTasks.get() == 0) {
            fireDriftBottleCreatingTask();
        }
    }

    @Override
    public void onCreateDriftBottleSucceed(String message) {
        mView.showError("The drift bottle has been dropped!");
        mView.hideProgress();
        mView.finish();
    }

    @Override
    public void onCreateDriftBottleFailed(String error) {
        mView.showError("Something went wrong.");
        mView.hideProgress();
    }

    @Override
    public void onUploadAudioSucceed(String location) {
        mDriftBottle.setAudioUrl(location);
        if (mNumOfBackgroundTasks.decrementAndGet() == 0) {
            fireDriftBottleCreatingTask();
        }
    }

    @Override
    public void onUploadAudioFailed(String error) {
        mView.showError(error);
        if (mNumOfBackgroundTasks.decrementAndGet() == 0) {
            fireDriftBottleCreatingTask();
        }
    }

    @Override
    public void onUploadDriftBottlePhotoSucceed(String location) {
        mDriftBottle.setPhotoUrl(location);
        if (mNumOfBackgroundTasks.decrementAndGet() == 0) {
            fireDriftBottleCreatingTask();
        }
    }

    @Override
    public void onUploadDriftBottlePhotoFailed(String error) {
        mView.showError(error);
        if (mNumOfBackgroundTasks.decrementAndGet() == 0) {
            fireDriftBottleCreatingTask();
        }
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

    private void fireDriftBottleCreatingTask() {
        CreateDriftBottleInteractor
                interactor = new CreateDriftBottleInteractorImpl(mExecutor,
                mMainThread, this, mDriftBottleRepository, mDriftBottle);
        interactor.execute();
    }
}
