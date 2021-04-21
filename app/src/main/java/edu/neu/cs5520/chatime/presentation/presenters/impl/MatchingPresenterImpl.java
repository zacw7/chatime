package edu.neu.cs5520.chatime.presentation.presenters.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UnsubscribeTopicInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.UnsubscribeTopicInteractorImpl;
import edu.neu.cs5520.chatime.domain.repository.TopicRepository;
import edu.neu.cs5520.chatime.presentation.presenters.MatchingPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;

public class MatchingPresenterImpl extends AbstractPresenter implements MatchingPresenter,
        UnsubscribeTopicInteractor.Callback {

    private MatchingPresenter.View mView;
    private TopicRepository mTopicRepository;

    public MatchingPresenterImpl(Executor executor, MainThread mainThread, View view,
            TopicRepository topicRepository) {
        super(executor, mainThread);
        mView = view;
        mTopicRepository = topicRepository;
    }

    @Override
    public void onUnsubscribeTopicSucceed(String message) {

    }

    @Override
    public void onUnsubscribeTopicFailed(String error) {

    }

    @Override
    public void cancel() {
        UnsubscribeTopicInteractor interactor = new UnsubscribeTopicInteractorImpl(mExecutor,
                mMainThread, this, mTopicRepository);
        interactor.execute();
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
        mView.showMessage(message);
    }
}
