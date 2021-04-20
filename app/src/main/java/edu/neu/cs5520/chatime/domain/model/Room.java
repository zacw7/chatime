package edu.neu.cs5520.chatime.domain.model;

import com.google.firebase.Timestamp;

public class Room {
    private String mId;
    private String mTopic;
    private String mRecipientUid;
    private String mRecipientUsername;
    private Timestamp mCreatedAt;
    private Timestamp mLastMessageTime;

    public Room() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public String getRecipientUid() {
        return mRecipientUid;
    }

    public void setRecipientUid(String recipientUid) {
        mRecipientUid = recipientUid;
    }

    public String getRecipientUsername() {
        return mRecipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        mRecipientUsername = recipientUsername;
    }

    public Timestamp getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        mCreatedAt = createdAt;
    }

    public Timestamp getLastMessageTime() {
        return mLastMessageTime;
    }

    public void setLastMessageTime(Timestamp lastMessageTime) {
        mLastMessageTime = lastMessageTime;
    }
}
