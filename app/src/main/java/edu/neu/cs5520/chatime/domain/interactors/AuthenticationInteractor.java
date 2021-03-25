package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;

public interface AuthenticationInteractor extends Interactor {

    interface Callback {

        void onSignInSucceeded(String message);

        void onSignInFailed(String error);
    }
}
