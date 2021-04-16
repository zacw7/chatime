package edu.neu.cs5520.chatime.domain.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.functions.HttpsCallableResult;

import edu.neu.cs5520.chatime.domain.model.User;

public interface UserRepository {
    User getCurrentUser();

    void getProfile(OnCompleteListener<User> onCompleteListener);

    void updateProfile(String username, String about, OnCompleteListener<HttpsCallableResult> onCompleteListener);

    void updatePhotoUrl(String photoUrl, OnCompleteListener<Void> onCompleteListener);
}
