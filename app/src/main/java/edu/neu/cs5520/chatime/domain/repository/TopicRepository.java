package edu.neu.cs5520.chatime.domain.repository;

public interface TopicRepository {
    static final String DEFAULT_TOPIC = "#DEFAULT_TOPIC#";

    void subscribeTopic(String userId, String topic);

    void unsubscribeTopic(String userId, String topic);

}
