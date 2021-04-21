package edu.neu.cs5520.chatime.presentation.ui.viewmodel;


import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.neu.cs5520.chatime.domain.repository.TopicRepository;

public class ChatViewModel {
    String roomId;
    String mUsername;
    String mTopic;
    String mLastTime;

    private static final SimpleDateFormat DATETIME_FMT = new SimpleDateFormat("MMM dd, HH:mm a", Locale.US);

    public ChatViewModel() {

    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String topic) {
        if (!TopicRepository.DEFAULT_TOPIC.equals(topic)) {
            mTopic = topic;
        }
    }

    public String getLastTime() {
        return mLastTime;
    }

    public void setLastTime(String lastTime) {
        mLastTime = lastTime;
    }

    public void setLastTime(Timestamp timestamp) {
        mLastTime = DATETIME_FMT.format(timestamp.toDate());
    }
}
