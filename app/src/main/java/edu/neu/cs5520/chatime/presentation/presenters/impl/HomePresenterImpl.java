package edu.neu.cs5520.chatime.presentation.presenters.impl;

import android.content.Intent;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.CreateDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.PickDriftBottleInteractor;
import edu.neu.cs5520.chatime.domain.interactors.SubscribeTopicInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.CreateDriftBottleInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.PickDriftBottleInteractorImpl;
import edu.neu.cs5520.chatime.domain.interactors.impl.SubscribeTopicInteractorImpl;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;
import edu.neu.cs5520.chatime.domain.repository.TopicRepository;
import edu.neu.cs5520.chatime.presentation.presenters.HomePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.activities.MatchingActivity;

public class HomePresenterImpl extends AbstractPresenter implements HomePresenter,
        PickDriftBottleInteractor.Callback, CreateDriftBottleInteractor.Callback,
        SubscribeTopicInteractor.Callback {

    private HomePresenter.View mView;
    private DriftBottleRepository mDriftBottleRepository;
    private TopicRepository mTopicRepository;

    public HomePresenterImpl(Executor executor, MainThread mainThread,
            HomePresenter.View view, DriftBottleRepository driftBottleRepository,
            TopicRepository topicRepository) {
        super(executor, mainThread);
        mView = view;
        mDriftBottleRepository = driftBottleRepository;
        mTopicRepository = topicRepository;
    }

    @Override
    public void pickRandomBottle() {
        mView.showProgress();

        // initialize the interactor
        PickDriftBottleInteractor interactor = new PickDriftBottleInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mDriftBottleRepository
        );

        // run the interactor
        interactor.execute();
    }

    @Override
    public void createDriftBottle(DriftBottle bottle) {
        CreateDriftBottleInteractor interactor = new CreateDriftBottleInteractorImpl(mExecutor,
                mMainThread,
                this,
                mDriftBottleRepository,
                bottle);
        interactor.execute();
    }

    @Override
    public void submitTopic(String userId, String topic) {
        SubscribeTopicInteractor interactor = new SubscribeTopicInteractorImpl(mExecutor, mMainThread,
                this, mTopicRepository, userId, topic);
        interactor.execute();
    }

    // callbacks
    @Override
    public void onSubscribeTopicSucceed(String message) {
        mView.showErrorMessage("onSubscribeTopicSucceed");
        mView.launchActivity(MatchingActivity.class);
    }

    @Override
    public void onSubscribeTopicFailed(String error) {
        mView.showErrorMessage(error);
    }

    @Override
    public void onPickingRandomBottleSucceed(DriftBottle bottle) {
        mView.hideProgress();
        mView.displayDriftBottle(bottle);
    }

    @Override
    public void onPickingRandomBottleFailed(String error) {

    }

    @Override
    public void onCreatingRandomBottleSucceed(String message) {
        mView.showCreatingSucceed();
    }

    @Override
    public void onCreatingRandomBottleFailed(String error) {

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
