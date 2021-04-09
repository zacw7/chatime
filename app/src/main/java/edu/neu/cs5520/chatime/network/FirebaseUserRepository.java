package edu.neu.cs5520.chatime.network;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import edu.neu.cs5520.chatime.domain.model.User;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;

public class FirebaseUserRepository implements UserRepository {

    private FirebaseAuth mAuth;

    public FirebaseUserRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            return null;
        }
        User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(),
                firebaseUser.getEmail(), firebaseUser.getPhotoUrl().toString());
        return user;
    }

    @Override
    public void updateUsername(String username, OnCompleteListener<Void> onCompleteListener) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();
            firebaseUser.updateProfile(profileUpdate).addOnCompleteListener(onCompleteListener);
        }
    }

    @Override
    public void updatePhotoUrl(String photoUrl, OnCompleteListener<Void> onCompleteListener) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(photoUrl))
                    .build();
            firebaseUser.updateProfile(profileUpdate).addOnCompleteListener(onCompleteListener);
        }
    }
}
