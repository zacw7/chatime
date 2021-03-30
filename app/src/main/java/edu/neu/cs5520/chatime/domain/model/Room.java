package edu.neu.cs5520.chatime.domain.model;

import java.util.List;

public class Room {
    private String mTopic;
    private List<User> mMembers;

    public Room() {
    }

    public Room(String topic, List<User> members) {
        this.mTopic = topic;
        this.mMembers = members;
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
