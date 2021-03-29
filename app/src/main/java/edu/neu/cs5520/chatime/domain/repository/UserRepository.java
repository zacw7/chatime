package edu.neu.cs5520.chatime.domain.repository;

import edu.neu.cs5520.chatime.domain.model.User;

public interface UserRepository {
    User getCurrentUser();
    void updateUsername(String username);
    void updatePhotoUri(String photoUrl);
}
