package edu.neu.cs5520.chatime.presentation.ui.viewmodel;

import edu.neu.cs5520.chatime.domain.model.User;

public class ProfileViewModel {
    private String mUsername;
    private String mEmail;
    private String mPhotoUrl;
    private String mAbout;
    private int mPoints;
    private int mLevel;
    private int mCurPoints;
    private boolean mCheckedIn;

    public ProfileViewModel(User user) {
        mUsername = user.getUsername();
        mEmail = user.getEmail();
        mPhotoUrl = user.getPhotoUrl();
        mAbout = user.getAbout();
        mPoints = user.getPoints();
        mLevel = 1 + mPoints / 100;
        mCurPoints = mPoints % 100;
        mCheckedIn = user.isCheckedIn();
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getAbout() {
        return mAbout;
    }

    public int getPoints() {
        return mPoints;
    }

    public int getLevel() {
        return mLevel;
    }

    public int getCurPoints() {
        return mCurPoints;
    }

    public boolean isCheckedIn() {
        return mCheckedIn;
    }
}
