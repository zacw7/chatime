package edu.neu.cs5520.chatime.presentation.ui.viewmodel;


import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.neu.cs5520.chatime.domain.repository.TopicRepository;

public class ChatViewModel implements Comparable<ChatViewModel> {
    String roomId;
    String mUsername;
    String mTopic;
    Timestamp mTsLast;
    Timestamp mTsCreated;
    String mLastTime;
    String mCreatedTime;

    private static final SimpleDateFormat DATETIME_FMT = new SimpleDateFormat("MMM dd, HH:mm a",
            Locale.US);

    public ChatViewModel() {

    }

    @Override
    public int compareTo(ChatViewModel o) {
        Timestamp thisTs = mTsLast == null ? mTsCreated : mTsLast;
        Timestamp oTs = o.getTsLast() == null ? o.getTsCreated() : o.getTsLast();
        return oTs.compareTo(thisTs);
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

    public Timestamp getTsLast() {
        return mTsLast;
    }

    public void setTsLast(Timestamp tsLast) {
        if (tsLast != null) {
            mLastTime = DATETIME_FMT.format(tsLast.toDate());
        }
        mTsLast = tsLast;
    }

    public Timestamp getTsCreated() {
        return mTsCreated;
    }

    public void setTsCreated(Timestamp tsCreated) {
        if (tsCreated != null) {
            mCreatedTime = DATETIME_FMT.format(tsCreated.toDate());
        }
        mTsCreated = tsCreated;
    }

    public String getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        mCreatedTime = createdTime;
    }

    public String getLastTime() {
        return mLastTime;
    }

    public void setLastTime(String lastTime) {
        mLastTime = lastTime;
    }
}
