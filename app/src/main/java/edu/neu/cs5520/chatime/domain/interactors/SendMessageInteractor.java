package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface SendMessageInteractor extends Interactor {
    interface Callback {
        void onSendMessageSucceed(String message);

        void onSendMessageFailed(String error);
    }
}
