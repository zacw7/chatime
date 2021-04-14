package edu.neu.cs5520.chatime.domain.model;

import com.google.firebase.Timestamp;

public class DriftBottle {
    private String mCreatorUid;
    private String mContent;
    private String mPhotoUrl;
    private String mAudioUrl;
    private Double mLatitude;
    private Double mLongitude;
    private boolean mAllowingMultipleReceivers;
    private Timestamp mCreatedAt;

    public DriftBottle() {
    }

    public String getCreatorUid() {
        return mCreatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        mCreatorUid = creatorUid;
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

    public boolean isAllowingMultipleReceivers() {
        return mAllowingMultipleReceivers;
    }

    public void setAllowingMultipleReceivers(boolean allowingMultipleReceivers) {
        mAllowingMultipleReceivers = allowingMultipleReceivers;
    }

    public Timestamp getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        mCreatedAt = createdAt;
    }
}
