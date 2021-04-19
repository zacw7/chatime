package edu.neu.cs5520.chatime.domain.interactors;

import java.util.List;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;

public interface GetBottleListInteractor extends Interactor {
    interface Callback {
        void onBottleListRetrieveSucceed(List<DriftBottle> bottleList);
        void onBottleListRetrievedFailed(String error);
    }
}
