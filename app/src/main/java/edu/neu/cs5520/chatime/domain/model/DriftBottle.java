package edu.neu.cs5520.chatime.domain.model;

import com.google.firebase.Timestamp;

public class DriftBottle {
    private String mId;
    private String mCreatorUid;
    private String mCreatorUsername;
    private String mContent;
    private String mPhotoUrl;
    private String mAudioUrl;
    private Double mLatitude;
    private Double mLongitude;
    private Timestamp mCreatedAt;
    private Timestamp mPickedAt;

    public DriftBottle() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCreatorUid() {
        return mCreatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        mCreatorUid = creatorUid;
    }

    public String getCreatorUsername() {
        return mCreatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        mCreatorUsername = creatorUsername;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public String getAudioUrl() {
        return mAudioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        mAudioUrl = audioUrl;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public Timestamp getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        mCreatedAt = createdAt;
    }

    public Timestamp getPickedAt() {
        return mPickedAt;
    }

    public void setPickedAt(Timestamp pickedAt) {
        mPickedAt = pickedAt;
    }
}
