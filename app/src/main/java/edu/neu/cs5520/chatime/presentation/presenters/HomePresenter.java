package edu.neu.cs5520.chatime.presentation.presenters;

import android.app.Activity;

import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;


public interface HomePresenter extends BasePresenter {

    void submitTopic(String topic);

    interface View extends BaseView {
        void showCreatingSucceed();

        void launchActivity(Class<? extends Activity> cls);

        void showErrorMessage(String message);
    }
}
