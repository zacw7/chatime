package edu.neu.cs5520.chatime.presentation.presenters;


import android.net.Uri;

import java.io.File;

import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;

public interface DriftBottlePresenter extends BasePresenter {
    interface View extends BaseView {
        void loadContentAndInfo(String content, String username, String datetime);

        void loadAudioPlayer(Uri uri);

        void loadPhoto(String url);

        void loadLocation(double latitude, double longitude);

        File getCacheFile(String filename);
    }
}