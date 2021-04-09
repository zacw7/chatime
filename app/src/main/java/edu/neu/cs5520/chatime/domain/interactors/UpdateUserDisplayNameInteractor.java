package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface UpdateUserDisplayNameInteractor extends Interactor {
    interface Callback {
        void onUserDisplayNameUpdateSucceed(String message);
        void onUserDisplayNameUpdateFailed(String message);
    }
}
