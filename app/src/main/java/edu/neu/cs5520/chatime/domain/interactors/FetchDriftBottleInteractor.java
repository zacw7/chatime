package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;

public interface FetchDriftBottleInteractor extends Interactor {
    interface Callback {
        void onFetchDriftBottleSucceed(DriftBottle driftBottle);

        void onFetchDriftBottleFailed(String error);
    }
}
