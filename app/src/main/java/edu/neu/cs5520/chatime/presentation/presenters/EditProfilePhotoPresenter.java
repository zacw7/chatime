package edu.neu.cs5520.chatime.presentation.presenters;


import android.net.Uri;

import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;

public interface EditProfilePhotoPresenter extends BasePresenter {

    void selectFile(Uri uri);

    void onUploadButtonClick();

    interface View extends BaseView {
        void displaySelectedPhoto(Uri uri);

        void closeItself();
    }
}
