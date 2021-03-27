package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface CreateDriftBottleInteractor extends Interactor {

    interface Callback {

        void onCreatingRandomBottleSucceed(String message);

        void onCreatingRandomBottleFailed(String error);
    }
}
