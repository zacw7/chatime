package edu.neu.cs5520.chatime.presentation.presenters;

import android.app.Activity;

import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;


public interface HomePresenter extends BasePresenter {

    void pickRandomBottle();

    void createDriftBottle(DriftBottle bottle);

    void submitTopic(String userId, String topic);

    interface View extends BaseView {
        void displayDriftBottle(DriftBottle bottle);

        void showCreatingSucceed();

        void launchActivity(Class<? extends Activity> cls);

        void showErrorMessage(String message);
    }
}
