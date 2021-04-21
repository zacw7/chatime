package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface MessageBottleCreatorInteractor extends Interactor {
    interface Callback {
        void onMessageBottleCreatorSucceed(String roomId);

        void onMessageBottleCreatorFailed(String error);
    }
}
