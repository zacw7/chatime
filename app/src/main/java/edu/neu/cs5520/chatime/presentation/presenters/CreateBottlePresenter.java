package edu.neu.cs5520.chatime.presentation.presenters;


import android.net.Uri;

import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;

public interface CreateBottlePresenter extends BasePresenter {

    void addPhoto(Uri uri);
    void removePhoto();

    interface View extends BaseView {
        void displayAddedPhoto(Uri uri);
        void hidePhotoPreview();
    }
}
