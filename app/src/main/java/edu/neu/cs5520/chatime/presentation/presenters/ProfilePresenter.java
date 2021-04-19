package edu.neu.cs5520.chatime.presentation.presenters;


import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.ProfileViewModel;

public interface ProfilePresenter extends BasePresenter {

    void editProfile(String username, String aboutMe);

    void dailyCheckIn();

    interface View extends BaseView {
        void resetElements();

        void loadUserProfile(ProfileViewModel user);

        void disableEdit();
    }
}
