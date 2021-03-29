package edu.neu.cs5520.chatime.presentation.presenters.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.repository.CurrentChatroomIdRepository;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.MainPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter {

    private MainPresenter.View mView;
    private CurrentChatroomIdRepository mCurrentChatroomIdRepository;
    private UserRepository mUserRepository;

    public MainPresenterImpl(Executor executor, MainThread mainThread,
            View view, CurrentChatroomIdRepository currentChatroomIdRepository,
            UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mCurrentChatroomIdRepository = currentChatroomIdRepository;
        mUserRepository = userRepository;
    }

    @Override
    public void resume() {
        mCurrentChatroomIdRepository.resetCurrentChatRoomId();
        if (mUserRepository.getCurrentUser() == null) {
            mView.showSignIn();
        }
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
