package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface ThrowBackDriftBottleInteractor extends Interactor {
    interface Callback {
        void onThrowBackDriftBottleSucceed(String message);

        void onThrowBackDriftBottleFailed(String error);
    }
}
