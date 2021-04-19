package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;

public interface PickDriftBottleInteractor extends Interactor {
    interface Callback {
        void onPickDriftBottleSucceed(DriftBottle driftBottle);

        void onPickDriftBottleFailed(String error);
    }
}
