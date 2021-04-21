package edu.neu.cs5520.chatime.presentation.presenters.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.PickDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.SubscribeTopicInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.PickDriftBottleInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.SubscribeTopicInteractorImpl;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;
import edu.neu.cs5520.chatime.domain.repository.TopicRepository;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.HomePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.activities.MatchingActivity;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;

public class HomePresenterImpl extends AbstractPresenter implements HomePresenter,
        SubscribeTopicInteractor.Callback, PickDriftBottleInteractor.Callback {

    private HomePresenter.View mView;
    private TopicRepository mTopicRepository;
    private UserRepository mUserRepository;
    private DriftBottleRepository mDriftBottleRepository;

    public HomePresenterImpl(Executor executor, MainThread mainThread,
            HomePresenter.View view,
            TopicRepository topicRepository,
            UserRepository userRepository,
            DriftBottleRepository driftBottleRepository) {
        super(executor, mainThread);
        mView = view;
        mTopicRepository = topicRepository;
        mUserRepository = userRepository;
        mDriftBottleRepository = driftBottleRepository;
    }

    @Override
    public void pickDriftBottle() {
        PickDriftBottleInteractor interactor = new PickDriftBottleInteractorImpl(mExecutor,
                mMainThread,
                this,
                mDriftBottleRepository);
        interactor.execute();
    }

    @Override
    public void submitTopic(String topic) {
        SubscribeTopicInteractor interactor = new SubscribeTopicInteractorImpl(mExecutor,
                mMainThread,
                this, mTopicRepository, topic);
        interactor.execute();
    }

    // callbacks
    @Override
    public void onSubscribeTopicSucceed(String message) {
        mView.launchActivity(MatchingActivity.class);
    }

    @Override
    public void onSubscribeTopicFailed(String error) {
        mView.showErrorMessage(error);
    }

    @Override
    public void onPickDriftBottleSucceed(DriftBottle driftBottle) {
        mView.displayBottle(new DriftBottleViewModel(driftBottle));
    }

    @Override
    public void onPickDriftBottleFailed(String error) {
        mView.showErrorMessage(error);
    }

    @Override
    public void resume() {

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

    }
}
