package edu.neu.cs5520.chatime.presentation.ui.viewmodel;

import edu.neu.cs5520.chatime.domain.repository.TopicRepository;

public class ChatViewModel {
    String roomId;
    String mUsername;
    String mTopic;
    String mPictureUrl;

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

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }
}
