package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface UploadFileInteractor extends Interactor {
    interface Callback {
        void onUploadFileSucceed(String location);
        void onUploadFileFailed(String message);
    }
}
