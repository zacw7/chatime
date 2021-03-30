package edu.neu.cs5520.chatime.domain.model;

public class User {
    private String mUid;
    private String mUsername;
    private String mEmail;
    private String mPictureUrl;

    public User() {
    }

    public User(String uid, String username, String email, String pictureUrl) {
        mUid = uid;
        mUsername = username;
        mEmail = email;
        mPictureUrl = pictureUrl;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUid='" + mUid + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPictureUrl='" + mPictureUrl + '\'' +
                '}';
    }
}
