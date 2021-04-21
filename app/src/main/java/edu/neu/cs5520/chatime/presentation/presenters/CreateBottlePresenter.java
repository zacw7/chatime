package edu.neu.cs5520.chatime.presentation.presenters;


import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;

public interface CreateBottlePresenter extends BasePresenter {

    void addPhoto(Uri uri);

    void removePhoto();

    void startAudioAdding();

    void cancelAudioAdding();

    void addAudio(Uri uri);

    void removeAudio();

    void startLocationAdding();

    void cancelLocationAdding();

    void addLocation(LatLng location);

    void removeLocation();

    void createBottle(String content);

    interface View extends BaseView {
        void displayAddedPhoto(Uri uri);

        void hidePhotoPreview();

        void startAudioRecorder();

        void closeAudioRecorder();

        void startAudioPlayer();

        void closeAudioPlayer();

        void closeAudioSection();

        void startLocationPicker();

        void saveLocation(String location);

        void clearLocation();

        void closeLocationPicker();

        void finish();
    }
}
