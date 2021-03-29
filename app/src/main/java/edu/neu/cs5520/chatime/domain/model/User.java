package edu.neu.cs5520.chatime.domain.model;

public class User {
    private String mUid;
    private String mUsername;
    private String mEmail;
    private String mProfileUrl;

    public User() {
    }

    public User(String uid, String username, String email, String profileUrl) {
        mUid = uid;
        mUsername = username;
        mEmail = email;
        mProfileUrl = profileUrl;
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

    public String getProfileUrl() {
        return mProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        mProfileUrl = profileUrl;
    }
}
