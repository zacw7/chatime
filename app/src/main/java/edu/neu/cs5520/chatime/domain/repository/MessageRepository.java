package edu.neu.cs5520.chatime.domain.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;

import edu.neu.cs5520.chatime.domain.model.Message;

public interface MessageRepository {
    void sendMessage(String chatroomId, Message message);
    void setEventListener(String chatroomId, EventListener<DocumentSnapshot> listener);
}
