package edu.neu.cs5520.chatime.domain.repository;

public interface TopicRepository {
    void subscribeTopic(String userId, String topic);

    void unsubscribeTopic(String userId, String topic);
}
