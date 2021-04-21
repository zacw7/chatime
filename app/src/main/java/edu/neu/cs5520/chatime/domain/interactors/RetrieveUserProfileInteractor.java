package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;
import edu.neu.cs5520.chatime.domain.model.User;

public interface RetrieveUserProfileInteractor extends Interactor {
    interface Callback {
        void onRetrieveUserProfileSucceed(User user);

        void onRetrieveUserProfileFailed(String error);
    }
}
