package edu.neu.cs5520.chatime.presentation.presenters;


import android.net.Uri;

import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;

public interface CreateBottlePresenter extends BasePresenter {

    void addPhoto(Uri uri);
    void removePhoto();
    void startAudioAdding();
    void cancelAudioAdding();
    void addAudio(Uri uri);
    void removeAudio();

    interface View extends BaseView {
        void displayAddedPhoto(Uri uri);
        void hidePhotoPreview();
        void startAudioRecorder();
        void closeAudioRecorder();
        void startAudioPlayer();
        void closeAudioPlayer();
        void closeAudioSection();
    }
}
