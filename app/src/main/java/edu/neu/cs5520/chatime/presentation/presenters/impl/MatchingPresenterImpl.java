package edu.neu.cs5520.chatime.presentation.presenters.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.presentation.presenters.MatchingPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class MatchingPresenterImpl extends AbstractPresenter implements MatchingPresenter {

    private MatchingPresenter.View mView;

    public MatchingPresenterImpl(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        mView = view;
    }

    @Override
    public void matchTimeout() {
        mView.backToHome();
    }

    @Override
    public void checkRoomId(String roomId) {
        if (roomId != null) {
            mView.cancelMatching();
            mView.enterChatRoom(roomId);
        }
    }

    @Override
    public void resume() {
        mView.startMatching();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        mView.showError(message);
    }
}
