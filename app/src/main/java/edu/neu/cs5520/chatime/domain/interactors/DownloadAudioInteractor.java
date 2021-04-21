package edu.neu.cs5520.chatime.domain.interactors;


import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface DownloadAudioInteractor extends Interactor {
    interface Callback {
        void onDownloadAudioSucceed(String message);

        void onDownloadAudioFailed(String error);
    }
}
