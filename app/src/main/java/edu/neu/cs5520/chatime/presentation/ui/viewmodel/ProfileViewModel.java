package edu.neu.cs5520.chatime.presentation.ui.viewmodel;

import edu.neu.cs5520.chatime.domain.model.User;

public class ProfileViewModel {
    private String mUsername;
    private String mEmail;
    private String mPhotoUrl;
    private String mAbout;
    private int mScore;
    private int mLevel;
    private int mCurScore;

    public ProfileViewModel(User user) {
        mUsername = user.getUsername();
        mEmail = user.getEmail();
        mPhotoUrl = user.getPhotoUrl();
        mAbout = user.getAbout();
        mScore = user.getScore();
        mLevel = 1 + mScore / 100;
        mCurScore = mScore % 100;
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

    public int getScore() {
        return mScore;
    }

    public int getLevel() {
        return mLevel;
    }

    public int getCurScore() {
        return mCurScore;
    }
}
