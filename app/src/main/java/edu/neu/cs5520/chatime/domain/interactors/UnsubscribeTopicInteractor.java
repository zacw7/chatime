package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface UnsubscribeTopicInteractor extends Interactor {
    interface Callback {
        void onUnsubscribeTopicSucceed(String message);

        void onUnsubscribeTopicFailed(String error);
    }
}
