package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.CreateBottlePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class CreateBottlePresenterImpl extends AbstractPresenter implements
        CreateBottlePresenter {

    private final String TAG = "EditProfilePhotoPresenter";
    private final String FOLDER = "profiles";
    private View mView;
    private StorageRepository mStorageRepository;
    private UserRepository mUserRepository;
    private Uri mPhotoUri;
    private Uri mAudioUri;
    private LatLng mLocation;
    private boolean mAllowMultipleReceivers;

    public CreateBottlePresenterImpl(Executor executor, MainThread mainThread, View view,
            StorageRepository storageRepository, UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mStorageRepository = storageRepository;
        mUserRepository = userRepository;
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
    public void setAllowMultipleReceivers(boolean allowMultipleUser) {
        mAllowMultipleReceivers = allowMultipleUser;
    }

    @Override
    public void submitBottle(String content) {

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
