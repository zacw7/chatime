package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.SubscribeTopicInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.TopicRepository;

public class SubscribeTopicInteractorImpl extends AbstractInteractor implements
        SubscribeTopicInteractor {

    private Callback mCallback;
    private TopicRepository mTopicRepository;
    private String mTopic;

    public SubscribeTopicInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            TopicRepository topicRepository,
            String topic) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mTopicRepository = topicRepository;
        mTopic = topic;
    }

    @Override
    public void run() {
        try {
            mTopicRepository.subscribeTopic(mTopic);
            mMainThread.post(() -> mCallback.onSubscribeTopicSucceed("Succeed!"));
        } catch (Exception e) {
            e.printStackTrace();
            mMainThread.post(() -> mCallback.onSubscribeTopicFailed("Submit failed"));
        }
    }
}
