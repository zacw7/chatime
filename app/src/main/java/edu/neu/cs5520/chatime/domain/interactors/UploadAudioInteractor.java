package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface UploadAudioInteractor extends Interactor {
    interface Callback {
        void onUploadAudioSucceed(String location);
        void onUploadAudioFailed(String error);
    }
}
