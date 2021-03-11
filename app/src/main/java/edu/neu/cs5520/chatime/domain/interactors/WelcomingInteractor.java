package edu.neu.cs5520.chatime.domain.interactors;


import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;


public interface WelcomingInteractor extends Interactor {

  interface Callback {

    void onMessageRetrieved(String message);

    void onRetrievalFailed(String error);
  }
}
