package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface SubscribeTopicInteractor extends Interactor {
    interface Callback {
        void onSubscribeTopicSucceed(String message);

        void onSubscribeTopicFailed(String error);
    }
}
