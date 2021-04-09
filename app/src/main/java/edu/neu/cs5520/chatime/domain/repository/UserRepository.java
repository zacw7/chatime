package edu.neu.cs5520.chatime.domain.repository;

import com.google.android.gms.tasks.OnCompleteListener;

import edu.neu.cs5520.chatime.domain.model.User;

public interface UserRepository {
    User getCurrentUser();

    void updateUsername(String username, OnCompleteListener<Void> onCompleteListener);

    void updatePhotoUrl(String photoUrl, OnCompleteListener<Void> onCompleteListener);
}
