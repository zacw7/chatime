package edu.neu.cs5520.chatime.presentation.presenters;

import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;

public interface MatchingPresenter extends BasePresenter {

    void enterChatRoom(String room);

    void matchTimeout();

    interface View extends BaseView {
        void startMatching();

        void cancelMatching();

        void backToHome();

        void enterChatRoom(String roomId);
    }
}
