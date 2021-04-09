package edu.neu.cs5520.chatime.presentation.presenters;


import edu.neu.cs5520.chatime.domain.model.User;
import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;

public interface ProfilePresenter extends BasePresenter {

    void editProfile(String username);

    interface View extends BaseView {
        void resetElements();

        void loadUserProfile(User user);

        void disableEdit();
    }
}
