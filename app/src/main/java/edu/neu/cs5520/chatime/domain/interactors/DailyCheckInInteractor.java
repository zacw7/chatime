package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface DailyCheckInInteractor extends Interactor {
    interface Callback {
        void onDailyCheckInSucceed(String message);
        void onDailyCheckInFailed(String error);
    }
}
