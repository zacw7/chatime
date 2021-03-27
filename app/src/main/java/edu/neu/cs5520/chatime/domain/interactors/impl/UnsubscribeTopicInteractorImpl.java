package edu.neu.cs5520.chatime.domain.interactors.impl;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UnsubscribeTopicInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.TopicRepository;

public class UnsubscribeTopicInteractorImpl extends AbstractInteractor implements
        UnsubscribeTopicInteractor {

    private Callback mCallback;
    private TopicRepository mTopicRepository;
    private String mUserId;
    private String mTopic;

    public UnsubscribeTopicInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            TopicRepository topicRepository,
            String userId,
            String topic) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mTopicRepository = topicRepository;
        mUserId = userId;
        mTopic = topic;
    }

    @Override
    public void run() {
        try {
            mTopicRepository.unsubscribeTopic(mUserId, mTopic);
            mMainThread.post(() -> mCallback.onUnsubscribeTopicSucceed("Succeed!"));
        } catch (Exception e) {
            mMainThread.post(() -> mCallback.onUnsubscribeTopicFailed("Submit failed"));
        }
    }
}
