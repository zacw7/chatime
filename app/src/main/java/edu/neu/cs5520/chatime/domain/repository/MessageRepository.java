package edu.neu.cs5520.chatime.domain.repository;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

public interface MessageRepository {
    void sendMessage(String to, String content);
    void setMessageEventListener(EventListener<QuerySnapshot> listener);
}
