package edu.neu.cs5520.chatime.network;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.model.User;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;

public class FirebaseUserRepository implements UserRepository {

    private FirebaseAuth mAuth;
    private final String TAG = "UserRepository";
    private FirebaseFunctions mFunctions;

    public FirebaseUserRepository() {
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            return null;
        }
        User user = new User();
        user.setUid(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        if (firebaseUser.getPhotoUrl() == null) {
            user.setPhotoUrl("gs://cs5520-chatime.appspot.com/profiles/default.png");
        } else {
            user.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
        }
        user.setUsername(firebaseUser.getDisplayName());
        return user;
    }

    @Override
    public void getProfile(OnCompleteListener<User> onCompleteListener) {
        mFunctions.getHttpsCallable("getProfile").call().continueWith(
                task -> {
                    Map<String, Object> map = (Map) task.getResult().getData();
                    User user = new User();
                    user.setUid((String) map.get("uid"));
                    user.setUsername((String) map.get("displayName"));
                    user.setEmail((String) map.get("email"));
                    user.setPhotoUrl((String) map.get("photoUrl"));
                    user.setAbout((String) map.get("about"));
                    user.setScore((int)map.get("scores"));
                    return user;
                }).addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void updateProfile(String username, String about,
            OnCompleteListener<HttpsCallableResult> onCompleteListener) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("about", about);

        mFunctions.getHttpsCallable("updateProfile").call(data).addOnCompleteListener(
                onCompleteListener);

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
