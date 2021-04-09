package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface UpdateUserProfilePhotoInteractor extends Interactor {
    interface Callback {
        void onUserProfilePhotoUpdateSucceed(String message);
        void onUserProfilePhotoUpdateFailed(String message);
    }
}
