package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface UploadDriftBottlePhotoInteractor extends Interactor {
    interface Callback {
        void onUploadDriftBottlePhotoSucceed(String location);

        void onUploadDriftBottlePhotoFailed(String error);
    }
}
