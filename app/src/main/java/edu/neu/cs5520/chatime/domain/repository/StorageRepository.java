package edu.neu.cs5520.chatime.domain.repository;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

public interface StorageRepository {
    void upload(Uri file, String folder,
            OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener,
            OnFailureListener onFailureListener);
}
