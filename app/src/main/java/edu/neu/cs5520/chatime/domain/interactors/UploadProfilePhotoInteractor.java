package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface UploadProfilePhotoInteractor extends Interactor {
    interface Callback {
        void onUploadProfilePhotoSucceed(String location);
        void onUploadProfilePhotoFailed(String error);
    }
}
