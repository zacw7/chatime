package edu.neu.cs5520.chatime.presentation.presenters.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.repository.CurrentChatroomIdRepository;
import edu.neu.cs5520.chatime.presentation.presenters.MatchingPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class MatchingPresenterImpl extends AbstractPresenter implements MatchingPresenter {

    private MatchingPresenter.View mView;
    private CurrentChatroomIdRepository mCurrentChatroomIdRepository;

    public MatchingPresenterImpl(Executor executor, MainThread mainThread, View view, CurrentChatroomIdRepository currentChatroomIdRepository) {
        super(executor, mainThread);
        mView = view;
        mCurrentChatroomIdRepository = currentChatroomIdRepository;
    }

    @Override
    public void matchTimeout() {
        mView.backToHome();
    }

    @Override
    public void checkIfMatched() {
        String chatroomId = mCurrentChatroomIdRepository.getCurrentChatRoomId();
        if (chatroomId != null) {
            mCurrentChatroomIdRepository.resetCurrentChatRoomId();
            mView.cancelMatching();
            mView.enterChatRoom(chatroomId);
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
