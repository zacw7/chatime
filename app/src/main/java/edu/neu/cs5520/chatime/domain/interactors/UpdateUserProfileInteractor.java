package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface UpdateUserProfileInteractor extends Interactor {
    interface Callback {
        void onUserProfileUpdateSucceed(String message);

        void onUserProfileUpdateFailed(String message);
    }
}
