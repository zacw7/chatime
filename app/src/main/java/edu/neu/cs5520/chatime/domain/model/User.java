package edu.neu.cs5520.chatime.domain.model;


public class User {
    private String mUid;
    private String mUsername;
    private String mEmail;
    private String mPhotoUrl;
    private String mAbout;
    private int mScore;

    public User() {
    }

    public User(String uid, String username, String email, String photoUrl, String about,
            int score) {
        mUid = uid;
        mUsername = username;
        mEmail = email;
        mPhotoUrl = photoUrl;
        mAbout = about;
        mScore = score;
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

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public String getAbout() {
        return mAbout;
    }

    public void setAbout(String about) {
        mAbout = about;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUid='" + mUid + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPhotoUrl='" + mPhotoUrl + '\'' +
                ", mAbout='" + mAbout + '\'' +
                ", mScore=" + mScore +
                '}';
    }
}
