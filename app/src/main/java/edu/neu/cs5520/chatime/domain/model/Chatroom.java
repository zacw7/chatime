package edu.neu.cs5520.chatime.domain.model;

import java.util.List;

public class Chatroom {
    private String mRoomId;
    private String mTopic;
    private List<User> mMembers;
    private List<Message> mMessages;

    public Chatroom() {
    }

    public Chatroom(String roomId, String topic, List<User> members,
            List<Message> messages) {
        this.mRoomId = roomId;
        this.mTopic = topic;
        this.mMembers = members;
        this.mMessages = messages;
    }

    public String getRoomId() {
        return mRoomId;
    }

    public void setRoomId(String roomId) {
        this.mRoomId = roomId;
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String topic) {
        this.mTopic = topic;
    }

    public List<User> getMembers() {
        return mMembers;
    }

    public void setMembers(List<User> members) {
        this.mMembers = members;
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public void setMessages(List<Message> messages) {
        this.mMessages = messages;
    }
}
