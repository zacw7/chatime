package edu.neu.cs5520.chatime.domain.model;


public class User {
    private String mUid;
    private String mUsername;
    private String mEmail;
    private String mPhotoUrl;
    private String mAbout;
    private int mPoints;
    private boolean mCheckedIn;

    public User() {
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

    public int getPoints() {
        return mPoints;
    }

    public void setPoints(int points) {
        mPoints = points;
    }

    public boolean isCheckedIn() {
        return mCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        mCheckedIn = checkedIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUid='" + mUid + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPhotoUrl='" + mPhotoUrl + '\'' +
                ", mAbout='" + mAbout + '\'' +
                ", mPoints=" + mPoints +
                ", mCheckedIn=" + mCheckedIn +
                '}';
    }
}
