package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface CreateDriftBottleInteractor extends Interactor {
    interface Callback {
        void onCreateDriftBottleSucceed(String message);
        void onCreateDriftBottleFailed(String error);
    }
}
