package edu.neu.cs5520.chatime.domain.model;

import java.util.List;

public class Room {
    private String mRoomId;
    private String mTopic;
    private List<User> mMembers;

    public Room() {
    }

    public Room(String roomId, String topic, List<User> members) {
        mRoomId = roomId;
        mTopic = topic;
        mMembers = members;
    }

    public String getRoomId() {
        return mRoomId;
    }

    public void setRoomId(String roomId) {
        mRoomId = roomId;
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
}
