package edu.neu.cs5520.chatime.domain.repository;

public interface TopicRepository {
    String DEFAULT_TOPIC = "#DEFAULT_TOPIC#";

    void subscribeTopic(String topic);

    void unsubscribe();

}
