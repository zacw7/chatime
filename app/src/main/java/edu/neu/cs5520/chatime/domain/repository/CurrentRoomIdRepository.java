package edu.neu.cs5520.chatime.domain.repository;

public interface CurrentRoomIdRepository {
    String getCurrentChatRoomId();

    void saveCurrentChatRoomId(String chatroomId);

    void resetCurrentChatRoomId();
}
